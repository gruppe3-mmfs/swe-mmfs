FROM node:lts AS build-stage
WORKDIR /app
COPY package*.json ./
COPY vite.config.ts ./
COPY tsconfig* ./
COPY . .
RUN npm install
RUN npm run build

FROM node:lts
COPY --from=build-stage /app/package*.json ./
COPY --from=build-stage /app/vite.config.ts ./
COPY --from=build-stage /app/tsconfig* ./
COPY --from=build-stage /app/node_modules ./node_modules
COPY --from=build-stage /app/dist ./dist
EXPOSE 4173
CMD ["npm", "run", "preview", "--", "--host", "0.0.0.0", "--port", "4173"]
