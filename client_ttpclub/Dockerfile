FROM node:18 AS builder

LABEL authors="maximuspokez"

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .
RUN npm run build

FROM nginx:1.23

COPY --from=builder /app/build /usr/share/nginx/html

EXPOSE 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]