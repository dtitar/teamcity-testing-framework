cd ..
teamcity_tests_directory=$(pwd)
workdir="teamcity_tests_infrastructure"
teamcity_server_workdir="teamcity_server"
teamcity_agent_workdir="teamcity_agent"
selenoid_workdir="selenoid"
teamcity_server_container_name="teamcity_server_instance"
teamcity_agent_container_name="teamcity_agent_instance"
selenoid_container_name="selenoid_instance"
selenoid_ui_container_name="selenoid_ui_instance"
workdir_names=($teamcity_server_workdir $teamcity_agent_workdir $selenoid_workdir)
container_names=($teamcity_server_container_name $teamcity_agent_container_name $selenoid_container_name $selenoid_ui_container_name)

####################
echo "Request IP"
export ips=$(ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1')
export ip="${ips%%$'\n'*}"
echo "Current IP: $ip"

####################
echo "Delete previous run data"

rm -rf $workdir
mkdir $workdir
cd $workdir

for dir in "${workdir_names[@]}"; do
  mkdir $dir
done

for container in "${container_names[@]}"; do
  docker stop $container
  docker rm $container
done

####################
echo "Start teamcity server"

cd $teamcity_server_workdir
docker run -d --name $teamcity_server_container_name  \
    -v $(pwd)/logs:/opt/teamcity/logs  \
    -p 8111:8111 \
    jetbrains/teamcity-server

echo "Teamcity Server is running..."

####################
echo "Pull Selenoid Video Container"

docker pull selenoid/video-recorder:latest-release
cd .. && cd $selenoid_workdir
mkdir video

####################
echo "Start selenoid"

mkdir config
cp $teamcity_tests_directory/infra/browsers.json config/

docker run -d                                   \
            --name $selenoid_container_name                                 \
            -p 4444:4444                                    \
            -v /var/run/docker.sock:/var/run/docker.sock    \
            -v $(pwd)/config/:/etc/selenoid/:ro              \
            -v $(pwd)/video/:/opt/selenoid/video/            \
            -e OVERRIDE_VIDEO_OUTPUT_DIR=$(pwd)/video/       \
    aerokube/selenoid:latest-release

image_names=($(awk -F'"' '/"image": "/{print $4}' "$(pwd)/config/browsers.json"))

echo "Pull all browser images: $image_names"

for image in "${image_names[@]}"; do
  docker pull $image
done

####################
echo "Start selenoid-ui"

docker run -d --name $selenoid_ui_container_name                                 \
            -p 80:8080 aerokube/selenoid-ui:latest-release --selenoid-uri "http://$ip:4444"

####################
echo "Create config.properties"

echo -e "host=$ip:8111\nremote=http://$ip:4444/wd/hub\nbrowser=firefox\nvideoStorage=http://$ip:4444/video" > $teamcity_tests_directory/src/main/resources/config.properties
cat $teamcity_tests_directory/src/main/resources/config.properties

####################
echo "Setup teamcity server"

cd .. && cd ..
mvn clean test -Dtest=SetupTest#startUpTest

####################
echo "Parse superuser token"
superuser_token=$(grep -o 'Super user authentication token: [0-9]*' $teamcity_tests_directory/$workdir/$teamcity_server_workdir/logs/teamcity-server.log | awk '{print $NF}')
echo "Super user token: $superuser_token"

####################
echo "Add superUserToken to config.properties"

echo -e "superUserToken=$superuser_token" >> $teamcity_tests_directory/src/main/resources/config.properties
cat $teamcity_tests_directory/src/main/resources/config.properties

####################
echo "Start teamcity agent"

cd $workdir && cd $teamcity_agent_workdir
docker run -d -e SERVER_URL="http://$ip:8111" \
    -v $(pwd)/conf:/data/teamcity-agent/conf --name $teamcity_agent_container_name \
    jetbrains/teamcity-agent

echo "Teamcity Agent is running..."

####################
echo "Setup teamcity agent"

cd .. && cd ..
mvn test -Dtest=SetupAgentTest#authorizeAgent

echo "Run API tests"
mvn test -DsuiteXmlFile=api-suite.xml

echo "Run UI tests"
mvn test -DsuiteXmlFile=ui-suite.xml

echo "Add swagger-coverage-report"
chmod +x .swagger-coverage-commandline/bin/swagger-coverage-commandline && \
.swagger-coverage-commandline/bin/swagger-coverage-commandline -s "http://$ip:8111/app/rest/swagger.json" -i target/swagger-coverage-output
