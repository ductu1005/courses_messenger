FROM nginx

# Xóa tệp cấu hình mặc định của Nginx
RUN rm /etc/nginx/conf.d/default.conf

# Sao chép tệp cấu hình của bạn vào trong container
COPY nginx.conf /etc/nginx/conf.d/