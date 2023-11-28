FROM node:latest

WORKDIR /projects

RUN npm install -g aglio --unsafe-perm
