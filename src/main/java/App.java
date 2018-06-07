import dao.Sql2oGameDao;
import dao.Sql2oDeveloperDao;
import dao.Sql2oReviewDao;
import models.Developer;
import models.Game;
import models.Review;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

import static spark.Spark.staticFileLocation;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString= "jdbc:h2:~/game-review.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oGameDao gameDao = new Sql2oGameDao(sql2o);
        Sql2oDeveloperDao developerDao = new Sql2oDeveloperDao(sql2o);
        Sql2oReviewDao reviewDao = new Sql2oReviewDao(sql2o);

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/developers", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            return new ModelAndView(model, "developer-list.hbs");
        }, new HandlebarsTemplateEngine());

        get("/developers/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            return new ModelAndView(model, "developer-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/developers/new", (request, response) -> {
            String newName = request.queryParams("name");
            Developer newDeveloper = new Developer(newName);
            developerDao.add(newDeveloper);
            response.redirect("/developers");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/developers/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            developerDao.clearAllDevelopers();
            gameDao.clearAllGames();
            response.redirect("/developers");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/developers/:devId", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            Integer devId = Integer.parseInt(request.params("devId"));
            Developer currentDeveloper = developerDao.findById(devId);
            model.put("developer", currentDeveloper);
            List<Game> devGames = gameDao.findGamesByDev(devId);
            model.put("games", devGames);
            return new ModelAndView(model, "developer-detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/developers/:devId/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            Integer devId = Integer.parseInt(request.params("devId"));
            Developer currentDeveloper = developerDao.findById(devId);
            model.put("developer", currentDeveloper);
            return new ModelAndView(model, "developer-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/developers/:devId/edit", (request, response) -> {
            Integer devId = Integer.parseInt(request.params("devId"));
            String newName = request.queryParams("name");
            developerDao.update(devId, newName);
            response.redirect("/developers/" + devId);
            return null;
        }, new HandlebarsTemplateEngine());

        get("/developers/:devId/delete", (request, response) -> {
            Integer devId = Integer.parseInt(request.params("devId"));
            developerDao.deleteById(devId);
            response.redirect("/developers");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/developers/:devId/games/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            Integer devId = Integer.parseInt(request.params("devId"));
            Developer currentDeveloper = developerDao.findById(devId);
            model.put("developer", currentDeveloper);
            return new ModelAndView(model, "game-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/developers/:devId/games/new", (request, response) -> {
            Integer devId = Integer.parseInt(request.params("devId"));
            String name = request.queryParams("name");
            String genre = request.queryParams("genre");
            Integer minPlayers = Integer.parseInt(request.queryParams("minPlayers"));
            Integer maxPlayers = Integer.parseInt(request.queryParams("maxPlayers"));
            Integer timeToPlay = Integer.parseInt(request.queryParams("timeToPlay"));
            Game newGame = new Game(name, genre, minPlayers, maxPlayers, timeToPlay, devId);
            gameDao.add(newGame);
            response.redirect("/developers/" + devId);
            return null;
        }, new HandlebarsTemplateEngine());

        get("/developers/:devId/games/:gameId", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            Integer devId = Integer.parseInt(request.params("devId"));
            Integer gameId = Integer.parseInt(request.params("gameId"));
            Developer currentDeveloper = developerDao.findById(devId);
            model.put("developer", currentDeveloper);
            Game currentGame = gameDao.findById(gameId);
            model.put("game", currentGame);
            List<Review> reviews = reviewDao.getReviewsByGameId(gameId);
            model.put("reviews", reviews);
            model.put("reviewCount", reviews.size());
            return new ModelAndView(model, "game-detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/developers/:devId/games/:gameId/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            Integer devId = Integer.parseInt(request.params("devId"));
            Developer currentDeveloper = developerDao.findById(devId);
            model.put("developer", currentDeveloper);
            Integer gameId = Integer.parseInt(request.params("gameId"));
            Game currentGame = gameDao.findById(gameId);
            model.put("game", currentGame);
            return new ModelAndView(model, "game-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/developers/:devId/games/:gameId/edit", (request, response) -> {
            Integer devId = Integer.parseInt(request.params("devId"));
            Integer gameId = Integer.parseInt(request.params("gameId"));
            String name = request.queryParams("name");
            String genre = request.queryParams("genre");
            Integer minPlayers = Integer.parseInt(request.queryParams("minPlayers"));
            Integer maxPlayers = Integer.parseInt(request.queryParams("maxPlayers"));
            Integer timeToPlay = Integer.parseInt(request.queryParams("timeToPlay"));
            Game newGame = new Game(name, genre, minPlayers, maxPlayers, timeToPlay, devId);
            gameDao.update(gameId, name, genre, minPlayers, maxPlayers, timeToPlay, devId);
            response.redirect("/developers/" + devId + "/games/" + gameId);
            return null;
        }, new HandlebarsTemplateEngine());

        get("/developers/:devId/games/:gameId/delete", (request, response) -> {
            Integer devId = Integer.parseInt(request.params("devId"));
            Integer gameId = Integer.parseInt(request.params("gameId"));
            gameDao.deleteById(gameId);
            response.redirect("/developers/" + devId);
            return null;
        }, new HandlebarsTemplateEngine());

        get("/games", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            List<Game> allGames = gameDao.getAll();
            model.put("games", allGames);
            return new ModelAndView(model, "developer-detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/games/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            model.put("noDev", true);
            return new ModelAndView(model, "game-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/games/new", (request, response) -> {
            String name = request.queryParams("name");
            String genre = request.queryParams("genre");
            Integer minPlayers = Integer.parseInt(request.queryParams("minPlayers"));
            Integer maxPlayers = Integer.parseInt(request.queryParams("maxPlayers"));
            Integer timeToPlay = Integer.parseInt(request.queryParams("timeToPlay"));
            Integer devId = Integer.parseInt(request.queryParams("devId"));
            Game newGame = new Game(name, genre, minPlayers, maxPlayers, timeToPlay, devId);
            gameDao.add(newGame);
            response.redirect("/games");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/games/delete", (request, response) -> {
            gameDao.clearAllGames();
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //POST sort game list
        post("/games/sort", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Developer> developers = developerDao.getAll();
            model.put("developers", developers);
            String sortBy = request.queryParams("sort");
            List<Game> games = gameDao.sort(sortBy);
            model.put("games", games);
            return new ModelAndView(model, "developer-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //POST new comment from game detail page
        post("/developers/:devId/games/:gameId/comments/new", (request, response) -> {
            String newComment = request.queryParams("comment");
            int newRating = Integer.parseInt(request.queryParams("rating"));
            int gameId = Integer.parseInt(request.params("gameId"));
            int devId = Integer.parseInt(request.params("devId"));
            Review newReview = new Review(newComment, newRating, gameId);
            reviewDao.add(newReview);
            gameDao.updateRating(gameId, reviewDao.getRatingsByGameId(gameId));
            response.redirect("/developers/" + devId + "/games/" + gameId);
            return null;
        }, new HandlebarsTemplateEngine());

        //GET delete review
        get("/developers/:devId/games/:gameId/comments/:id/delete", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            reviewDao.deleteById(id);
            int gameId = Integer.parseInt(request.params("gameId"));
            int devId = Integer.parseInt(request.params("devId"));
            gameDao.updateRating(gameId, reviewDao.getRatingsByGameId(gameId));
            response.redirect("/developers/" + devId + "/games/" + gameId);
            return null;
        }, new HandlebarsTemplateEngine());

        //GET edit review page
        get("/developers/:devId/games/:gameId/comments/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int devId = Integer.parseInt(request.params("devId"));
            int gameId = Integer.parseInt(request.params("gameId"));
            int commentId = Integer.parseInt(request.params("id"));
            Review currentReview = reviewDao.findById(commentId);
            model.put("review", currentReview);
            List<Developer> developers = developerDao.getAll();
            model.put("game", gameDao.findById(gameId));
            model.put("developer", developerDao.findById(devId));
            model.put("developers", developers);
            return  new ModelAndView(model, "comment-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //POST edit review
        post("/developers/:devId/games/:gameId/comments/:id/edit", (request, response) -> {
            int devId = Integer.parseInt(request.params("devId"));
            int gameId = Integer.parseInt(request.params("gameId"));
            int commentId = Integer.parseInt(request.params("id"));
            String newComment = request.queryParams("comment");
            int newRating = Integer.parseInt(request.queryParams("rating"));
            reviewDao.update(commentId, newComment, newRating, gameId);
            gameDao.updateRating(gameId, reviewDao.getRatingsByGameId(gameId));
            response.redirect("/developers/" + devId + "/games/" + gameId);
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
