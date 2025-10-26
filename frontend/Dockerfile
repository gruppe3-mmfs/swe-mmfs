FROM node:lts AS build-stage
WORKDIR /app
COPY . .
RUN npm install -g npm@latest
RUN npm ci
RUN npm run build

EXPOSE 4173
CMD ["npm", "run", "preview"]
