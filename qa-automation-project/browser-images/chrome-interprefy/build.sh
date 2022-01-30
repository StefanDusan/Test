#!/bin/bash
set -e

if [ "$#" -ne 1 ];
then
	echo "Usage: ./build.sh <version>"
	exit 1
fi

set -x

aws ecr get-login-password --region eu-west-1 | docker login --username AWS --password-stdin 046207729747.dkr.ecr.eu-west-1.amazonaws.com
docker build -t "046207729747.dkr.ecr.eu-west-1.amazonaws.com/qa/chrome:$1-interprefy" --build-arg VERSION=$1 .
docker push "046207729747.dkr.ecr.eu-west-1.amazonaws.com/qa/chrome:$1-interprefy"
echo "046207729747.dkr.ecr.eu-west-1.amazonaws.com/qa/chrome:$1-interprefy pushed successfully"
