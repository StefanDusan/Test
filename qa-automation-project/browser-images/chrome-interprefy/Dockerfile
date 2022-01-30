ARG VERSION
FROM quay.io/browsers/chrome:$VERSION
ADD --chown=selenium https://interprefy-downloads.s3.eu-west-1.amazonaws.com/video.y4m https://interprefy-downloads.s3.eu-west-1.amazonaws.com/audio.wav https://interprefy-downloads.s3.eu-west-1.amazonaws.com/1_MB_test_file.txt https://interprefy-downloads.s3.eu-west-1.amazonaws.com/eicar.com.txt /streams/
RUN chmod 644 /streams/*
