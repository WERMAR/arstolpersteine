# Use an official Node runtime as a base image
FROM --platform=$BUILDPLATFORM node:19 as build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install
RUN npx ngcc --properties es2023 browser module main --first-only --create-ivy-entry-points

# Copy the rest of the application code
COPY . .

# Build the Angular app
RUN npm run build

# Use a smaller, production-ready image
FROM nginx:stable
COPY default.conf /etc/nginx/conf.d
# Copy the built app from the previous stage to the nginx web root directory
COPY --from=build /app/dist/stolpersteine-ar-fe /usr/share/nginx/html

# Expose port 80
EXPOSE 80
