package dao;

import models.Developer;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDeveloperDao implements DeveloperDao{
    private final Sql2o sql2o;

    public Sql2oDeveloperDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Developer developer){
        String sql = "INSERT INTO developers (name) VALUES (:name)";
        try(Connection con =sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(developer)
                    .executeUpdate()
                    .getKey();
            developer.setId(id);
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Developer> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM developers")
                    .executeAndFetch(Developer.class);
        }
    }

    @Override
    public Developer findById(int id){
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM developers WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Developer.class);
        }
    }

    @Override
    public void update(int id, String name){
        String sql = "UPDATE developers SET (name) = (:name) WHERE id=:id";
        try(Connection con =sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id){
        try(Connection con = sql2o.open()){
            con.createQuery("DELETE from developers WHERE id=:id")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllDevelopers(){
        try(Connection con = sql2o.open()){
            con.createQuery("DELETE from developers")
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }


}
