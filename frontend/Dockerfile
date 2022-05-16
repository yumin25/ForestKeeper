FROM node:lts-alpine AS builder

WORKDIR /app
COPY package.json .

RUN apk update && apk upgrade && apk add --no-cache bash git openssh
RUN npm install --save --legacy-peer-deps
COPY . .
RUN npm run build

FROM nginx:alpine

COPY --from=builder /app/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/conf.d

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]
