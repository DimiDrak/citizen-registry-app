sudo docker network create citizen-net
sudo docker run -d --name mysql --network citizen-net --network-alias mysql -v mysql:/var/lib/mysql -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_DATABASE=citizens -e MYSQL_USER=citizen -e MYSQL_PASSWORD=citizen1234 mysql:8.3.0

while ! sudo docker exec -it mysql mysql -ucitizen -pcitizen1234 -e "SELECT 1" >/dev/null 2>&1; do
    sleep 1
done

sudo docker run -d --name citizen --network citizen-net -p 8080:8080 -e DB_HOST=mysql -e DB_USER=citizen -e DB_PASSWORD=citizen1234 -e DB_NAME=citizens citizen:0.1