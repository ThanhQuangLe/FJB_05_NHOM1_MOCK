package fa.mock.repository;

import fa.mock.entities.News;
import fa.mock.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    @Query("select n from News n ")
    Page<News> findByTittle(String searchTerm, Pageable pageable);

    @Query("select n from News n ")
    List<News> findAllNews();

    @Query("select n from News n ")
    Page<News> findAllNewsPaging(Pageable pageable);



}
