import dao.Sql2oGameDao;
import dao.Sql2oDeveloperDao;
import models.Developer;
import models.Game;
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
//            List<Game> devGames = gameDao.findGamesByDev(devId);
//            model.put("games", devGames);
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
    }
}
