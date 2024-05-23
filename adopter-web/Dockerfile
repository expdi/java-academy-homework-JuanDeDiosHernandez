# Stage 1: Compile and Build angular codebase

# # Use official node image as the base image
# FROM node:latest as builder

# # Set the working directory
# WORKDIR /usr/local/app

# # Add the source code to app
# COPY ./ /usr/local/app/

# # Install all the dependencies
# RUN npm install

# # Generate the build of the application
# RUN npm run build

# # Stage 2: Serve app with nginx server

# # Use official nginx image as the base image
# FROM nginx:1.25.2-alpine-slim

# # Copy the build output to replace the default nginx contents.
# COPY --from=builder /usr/local/app/build /usr/share/nginx/html

# # Expose port 4200
# EXPOSE 4200

# # Run nginx in the foreground
# CMD ["nginx", "-g", "daemon off;"]




# # Stage 1: Compile and Build angular codebase
# # Use official node image as the base image
# FROM node:18.14.1 AS build
# # Set the working directory
# WORKDIR /app
# COPY package.json package-lock.json ./
# # Change the ownership of package.json
# RUN chown -R node:node /app
# # Install all the dependencies
# RUN npm install
# # Add the source code to app
# COPY . ./
# # Generate the build of the application
# RUN npm run build
# # Stage 2: Serve app with nginx server
# # Use official nginx image as the base image
# FROM nginx:alpine
# COPY .nginx/nginx.conf /etc/nginx/nginx.conf
# WORKDIR /usr/share/nginx/html
# # Copy the build output to replace the default nginx contents.
# COPY --from=build /app/dist/adopter-web/ .
# # Expose port 4200
# EXPOSE 4200



# ### STAGE 1: Build ###
# FROM node:18.14.1 AS build
# WORKDIR /usr/src/app
# COPY package.json package-lock.json ./
# RUN npm install
# COPY . .
# RUN npm run build
# ### STAGE 2: Run ###
# FROM nginx:1.17.1-alpine
# COPY nginx.conf /etc/nginx/nginx.conf
# COPY --from=build /usr/src/app/dist/adopter-web /usr/share/nginx/html


# FROM node:18.14.1 AS build
# WORKDIR /usr/src/app
# COPY . /usr/src/app
# RUN npm install -g @angular/cli
# RUN npm install
# CMD ["ng", "serve", "--host", "0.0.0.0"]


### STAGE 1: Build ###
FROM node:18.14.1 AS build
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN npm install -g @angular/cli
RUN npm install
RUN npm run build
### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY nginx/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/adopter-web /usr/share/nginx/html
