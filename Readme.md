Build and Run Instructions

1. Clone the repository:
   git clone https://github.com/SerhiiBabanov/Candles.git

2. Navigate to the project root directory:
   cd Candles

3. Fill .env file with your credentials:  
   API_TOKEN=your_api_token;  
   API_TOKEN_NAME=your_api_token_name;  
   MAIL_PASSWORD=your_gmail_app_password;  
   MAIL_USERNAME=your_email;  
   You can find instructions how to create gmail app password here:  
        https://support.google.com/accounts/answer/185833?hl=en  
   MONGODB_URI=mongodb://rootuser:rootpass@localhost:27017; or your mongodb uri from MongoDb Atlas  
   TELEGRAM_BOT_TOKEN=yout_telegram_bot_token;  
   TELEGRAM_BOT_CHAT_ID=chat_id where you want to receive notifications;  
   You can find instructions how to create telegram bot and get chat id here:  
        https://docs.influxdata.com/kapacitor/v1/reference/event_handlers/telegram/  
   

3. Build the Docker images and start the containers using Docker Compose:
   docker-compose up --build

This command will build the Docker image for the Spring Boot application, pull the MongoDB image,
and start the containers.  
For reloading data in the MongoDB database by each restart you can change property in application.properties file:  
init.data.enabled=false to init.data.enabled=true. This will create new candles and boxes in the database and load images.  
Wait for the containers to start up and initialize.  
Once the containers are up and running, you can access the Spring Boot application at http://localhost:8082.  
Full text search is available only when database is on MongoDB Atlas and for candles and boxes created indexes.   
In local database full text search return empty value.  
