package felp.projects.todosimple.repositories;

import felp.projects.todosimple.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    //Formato de Query Usando SpringBoot + JPA
    List<Task> findByUser_Id(int id);

    //Formato de Query Usando SpringBoot + SQL
    //@Query(value = "SELECT t FROM Task t WHERE t.user.id = : id")
    //List<Task> findByUserId(@Param("id") Integer id);

    //Formato de Query Usando SQL puro
    //@Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    //List<Task> findByUserId(@Param("id") Integer id);
}
