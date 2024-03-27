package dev.ohhoonim.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ohhoonim.demo.model.Score;

/* 
 * findScoresByStudent 메소드를 subjectScoreCustom 인터페이스에서 정의하고 해당 인터페이스에서 구현
 */
public interface SubjectScoreRepository extends JpaRepository<Score, Long>, SubjectScoreCustom {

}
