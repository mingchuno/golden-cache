sudo apt-get update && sudo apt-get -y upgrade 
sudo apt-get install vim rsync git zip unzip
wget -qO- https://get.docker.com/ | sh
sudo usermod -aG docker ubuntu
sudo su
curl -L https://github.com/docker/compose/releases/download/1.5.2/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
