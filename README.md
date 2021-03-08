# My-OOP-endterm-project
This project is about a book of cooking recipes . The project has 5 classes:
1. A role where there are two options admin and user.
2. Recipe Categories where there are 4 recipe categories (breakfast, lunch, dinner, desserts).                                                                           3. Caloric 3. Calorie content this class describes (calories, fat, protein, carbohydrates) that a recipe contains .
4. Recipes in this class contains the name of the recipe, the description of the recipe and a step-by-step guide for cooking, the necessary ingredients .
5. User the class has all the data from the user's account ( first name, last name, nickname, password ), and what role this user has, as well as a list of recipes that he saved.

DB Manager is needed to connect to the database ,
Repository is needed in order to access the data in the database

The project has methods :

1.Login You can log in to your account using your username and password .

2.Registration can be made by registering, and kansol will ask you to fill in all the fields from the user class .
When registering, there is a method that checks the password and username for compliance with the requirements .

There are also two roles: user and admin . Everyone has their own methods.

3.Menu for the user .A method in which the user can update the parameters of their account, delete the account, save recipes, adjust from saved recipes, view saved recipes .

4.Menu for the admin . A method in which the administrator can update the parameters of his account, delete the account, save recipes, settle from saved recipes, view saved recipes, and add recipes . The admin can also view the list of users, add a user, update user parameters , delete a user .

5.Recipe menu . The user can view the entire list of recipes, search for recipes by name, and by category, and search for recipes by recipe calorie content .
