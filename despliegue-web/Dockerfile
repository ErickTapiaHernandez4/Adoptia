# Servidor web Nginx con la versión WEB (Wasm) de Adoptia
FROM nginx:1.27-alpine

# Configuración de Nginx (incluye el MIME de WebAssembly)
COPY nginx.conf /etc/nginx/nginx.conf

# Archivos de la web compilada
COPY sitio/ /usr/share/nginx/html/

EXPOSE 8080