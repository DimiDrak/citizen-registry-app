sudo docker container stop mysql citizen
sudo docker container rm mysql citizen
sudo docker network rm citizen-net
sudo docker system prune -f