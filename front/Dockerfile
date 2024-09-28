# 빌드 작업
# 1. Node 이미지 [20]
FROM node:20 AS build
# 2. 컨테이너에 작업 디렉토리 설정
WORKDIR /app
# 3. package.json & package-lock.json을 작업 디렉토리에 복사
COPY package*.json ./
# 4. 의존성 업데이트 및 설치
RUN npm update
RUN npm install
# 5. 소스 코드를 컨테이너에 복사
COPY . .
# 6. React 애플리케이션 빌드
RUN npm run build

# 배포
# 1. Nginx 이미지
FROM nginx:alpine
# 2. Nginx 설정 파일을 교체
COPY nginx.conf /etc/nginx/conf.d/default.conf
# 3. Nginx 기본 html 삭제
RUN rm -rf /usr/share/nginx/html/*
# 4. 빌드된 React 애플리케이션을 Nginx 디렉토리로 복사
COPY --from=build /app/build /usr/share/nginx/html

# 5. SSL 인증서 파일 복사 (호스트의 인증서 파일을 컨테이너에 복사)
# 인증서 파일은 Docker Compose의 `volumes`를 통해 마운트된다고 가정
COPY /etc/letsencrypt/live/inkyuinst.store/fullchain.pem /etc/ssl/certs/fullchain.pem
COPY /etc/letsencrypt/live/inkyuinst.store/privkey.pem /etc/ssl/private/privkey.pem

# 6. Nginx 포트 노출
EXPOSE 80
EXPOSE 443  # HTTPS 포트도 추가로 노출

# 7. Nginx 서버 실행
CMD ["nginx", "-g", "daemon off;"]
