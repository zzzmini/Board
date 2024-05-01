package com.example.myBoard.repository;

import com.example.myBoard.constant.Gender;
import com.example.myBoard.entity.Users;
import jakarta.transaction.Transactional;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UsersRepositoryTest {
    @Autowired
    UsersRepository usersRepository;
    @Test
    void findByName테스트(){
        String findName = "Hugo";
        usersRepository.findByName(findName).forEach(users -> System.out.println(users));
    }

    @Test
    void findTop3ByLikeColor테스트(){
        String color = "Pink";
        usersRepository.findTop3ByLikeColor(color)
                .forEach(users -> System.out.println(users));
    }
    @Test
    void findByGenerAndLikeColor테스트(){
        String color = "Pink";
        usersRepository.findByGenderAndLikeColor(Gender.Male, color)
                .forEach(users -> System.out.println(users));
    }

    @Test
    void findByCreatedAtAfter테스트() {
        usersRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(30L))
                .forEach(users -> System.out.println(users));
    }

    @Test
    void findByColorAndSort() {
        usersRepository.findByLikeColor("Orange",
                Sort.by(Sort.Order.asc("gender"),
                        Sort.Order.desc("createdAt")))
                .forEach(users -> System.out.println(users));
    }

    // 전체 페이징
    @Test
    void pagingTest(){
        System.out.println("페이지 = 0, 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id"))))
                    .getContent()
                    .forEach(users -> System.out.println(users));

        System.out.println("페이지 = 1, 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                        PageRequest.of(1, 5, Sort.by(Sort.Order.desc("id"))))
                .getContent()
                .forEach(users -> System.out.println(users));

        System.out.println("페이지 = 2, 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                        PageRequest.of(2, 5, Sort.by(Sort.Order.desc("id"))))
                .getContent()
                .forEach(users -> System.out.println(users));
    }

    @Test
    void pagingTest2(){
        Pageable pageable = PageRequest.of(30, 10);
        Page<Users> result = usersRepository.findByIdGreaterThanEqualOrderByIdDesc(
                200L, pageable);
        result.getContent().forEach(users -> System.out.println(users));
        // 총 페이지 수
        System.out.println("Total Pages : " + result.getTotalPages());
        // 전체 데이터 개수
        System.out.println("Total Contents Count : " + result.getTotalElements());
        // 현재 페이지 번호
        System.out.println("Current Page Number : " + result.getNumber());
        // 다음 페이지 존재 여부
        System.out.println("Next Page? " + result.hasNext());
        // 시작 페이지인지 여부
        System.out.println("Is First Page? " + result.isFirst());
    }

    @Test
    @DisplayName("문제 9")
    void Exam09(){
        usersRepository.findAll(
                        PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createdAt"))))
                .getContent()
                .forEach(users -> System.out.println(users));
    }

    @Test
    @DisplayName("문제 10")
    void Exam10(){
        Pageable pageable = PageRequest.of(1, 3);
        Page<Users> result = usersRepository.findByGenderOrderByIdDesc(Gender.Male, pageable);
        result.getContent().forEach(users -> System.out.println(users));
        LocalDate baseDate = LocalDate.now().minusMonths(1L);
    }

    @Test
    void 문제1(){
        List<Users> usersList = usersRepository
                .findByNameLikeOrNameLike("%w%", "%m%");
        for (Users user : usersList) {
            if (user.getGender().equals(Gender.Female)) {
                System.out.println(user);
            }
        }
        // 두번째 방법
        usersRepository.findByNameLikeAndGenderOrNameLikeAndGender(
                "%w%", Gender.Female, "%m%", Gender.Female
        ).forEach(users -> System.out.println(users));
    }

    //문제 2. 이메일에 net을 포함하는 데이터 건수를 출력하시오.
    @Test
    void 문제2(){
        System.out.println("Count = " +
                usersRepository.findByEmailLike("%net%").stream().count());
    }

    // 문제 3. 가장 최근 한달이내에 업데이트된 자료 중 이름 첫자가 "J"인 자료를 출력하시오.
    @Test
    void 문제3(){
        LocalDateTime date = LocalDateTime.now().minusMonths(1L);
        usersRepository
                .findByUpdatedAtGreaterThanEqualAndNameLike(date, "E%")
                .forEach(users -> System.out.println(users));
    }
    //문제 4. 가장 최근 생성된 자료 10건을 ID, 이름, 성별, 생성일 만 출력하시오.
    @Test
    void 문제4(){
        List<Users> users = usersRepository.findTop10ByOrderByCreatedAtDesc();
        for (Users user : users) {
            System.out.println("ID = " + user.getId() +
                    " , Name = " + user.getName() +
                    " , Gender = " + user.getGender() +
                    " , CreatedAt = " + user.getCreatedAt()
            );
        }
    }
    //문제 5. "Red"를 좋아하는 남성 이메일 계정 중 사이트를 제외한 계정만 출력하시오.
    @Test
    void 문제5(){
        List<Users> users = usersRepository.findByGenderAndLikeColor(Gender.Male, "Red");
        for (Users user : users) {
            String email = user.getEmail();
            String account = email.substring(0, email.indexOf("@"));
            System.out.println( "email : " + email +  ", email Account : " + account);
        }
    }
    //문제 6. 갱신일이 생성일 이전인 잘못된 데이터를 출력하시오.
    @Test
    void 문제6(){
        List<Users> users = usersRepository.findAll();
        for (Users user : users) {
            if (user.getUpdatedAt().isBefore(user.getCreatedAt())) {
                System.out.println(user);
            }
        }
    }
    //문제 7. 이메일에 edu를 갖는 여성 데이터를 가장 최근 데이터부터 보이도록 출력하시오.
    @Test
    void 문제7(){
        usersRepository
                .findByEmailLikeAndGenderOrderByCreatedAtDesc("%edu%", Gender.Female)
                .forEach(users -> System.out.println(users));
    }

    //문제 8. 좋아하는 색상 별로 오름차순 정렬하고 같은 색상 데이터는 이름의 내림차순으로 출력하시오.
    @Test
    void 문제8(){
        usersRepository.findAll(
                Sort.by(Sort.Order.asc("likeColor"),
                        Sort.Order.desc("name"))
        ).forEach(users -> System.out.println(users));
    }

    // 문제 9. 전체 자료를 가장 최근 입력한 자료 순으로 정렬 및 페이징 처리하고 한 페이지당 10건씩 출력하되,
    // 그 중 1번째 페이지를 출력하시오.
    @Test
    void 문제9() {
        usersRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createdAt")))
        ).getContent()
                .forEach(users -> System.out.println(users));
    }
    // 문제10. 남성 자료를 ID의 내림차순으로 정렬한 후 한페이당 3건을 출력하되 그 중 2번째 페이지 자료를  출력하시오.
    @Test
    void 문제10(){
        Pageable pageable = PageRequest.of(1, 3);
        Page<Users> result = usersRepository.findByGenderOrderByIdDesc(Gender.Male, pageable );
        result.getContent().forEach(users -> System.out.println(users));
    }

    // 문제11. 지난달의 모든 자료를 검색하여 출력하시오.
    @Test
    void 문제11(){
        //기준일
        LocalDate baseDate = LocalDate.now().minusMonths(1L);
        //시작일
        LocalDateTime startDate = baseDate.withDayOfMonth(1).atTime(0,0,0);
        //종료일
        LocalDateTime lastDate = baseDate
                .withDayOfMonth(baseDate.lengthOfMonth()).atTime(23,59,59);
        //검색
        System.out.println("startDate = " + startDate + ", lastDate = " + lastDate);
        usersRepository.findByCreatedAtBetween(startDate, lastDate)
                .forEach(users -> System.out.println(users));
    }
}