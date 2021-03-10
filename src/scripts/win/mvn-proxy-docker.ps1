# See https://github.com/aballaci/maven-docker-proxy
docker pull sonatype/nexus3
docker volume create --name nexus-data
docker run -d -p 8081:8081 --name nexus -v nexus-data:/nexus-data sonatype/nexus3

# Set up proxy to Jitpack (disable auto block so it will keep trying public things)
# Set up proxy to Artifactory (enable HTTP auth with automatron and API key)
# Then use: mvn -s .m2/settings.xml -Plocal-proxy -P!eri-profile