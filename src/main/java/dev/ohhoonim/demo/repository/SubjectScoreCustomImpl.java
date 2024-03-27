package dev.ohhoonim.demo.repository;

import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import dev.ohhoonim.demo.controller.dto.SubjectScoreResponse;
import dev.ohhoonim.demo.model.QScore;
import dev.ohhoonim.demo.model.QStudent;
import lombok.RequiredArgsConstructor;

/*
 * 표현할 sql문
 * select
 * st.student_id,
 * st.name,
 * st.age,
 * sc.subject,
 * sc.score
 * from sutdent st
 * join score sc on st.student_id= sc.studetn_id
 * where st.student_id=1
 * order by student_id;
 */

@RequiredArgsConstructor
public class SubjectScoreCustomImpl implements SubjectScoreCustom {

    // Config에 QueryDslConfig에서 queryfactroy 가져와 사용
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SubjectScoreResponse> findScoresByStudent(Long studentId) {

        /* Q클래스에서 간혹 score_id를 score1 등으로 변환할 수 있음 */
        QStudent st = QStudent.student;
        QScore sc = QScore.score1;

        // QueryDsl 리턴 타입은 JPAQuery<Tuple>이다
        JPAQuery<Tuple> query =
                // QueryDsl로 표현 같다는 eq
                queryFactory.select(
                        st.studentId,
                        st.name,
                        st.age,
                        sc.subject,
                        sc.score)
                        .from(st)
                        .join(sc)
                        .on(st.studentId.eq(sc.studentId))
                        .where(st.studentId.eq(studentId))
                        .orderBy(st.studentId.desc());

        // 위에 쿼리문을 실행
        return query.fetchJoin().fetch().stream().map(tuple -> SubjectScoreResponse.builder()
                .studentId(tuple.get(st.studentId))
                .name(tuple.get(st.name))
                .age(tuple.get(st.age))
                .subject(tuple.get(sc.subject))
                .score(tuple.get(sc.score))
                .build()).toList();
    }

}
