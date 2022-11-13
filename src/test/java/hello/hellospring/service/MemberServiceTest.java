package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach     //각 메서드가 실행되기 전에 이 메서드가 먼저 실행된다.
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        //이렇게 외부에서 넣어주는 방식을 DI(Dependency Injection:의존성 주입)이라고 한다.
    }

    @AfterEach      //메서드가 끝나면 이 메서드가 실행 된다.
    public void afterEach(){
        memberRepository.clearStore();    //MemoryMemberRepository에 만든 clearStore로 메서드가 끝날 때 마다 스토어를 비워준다.
    }

    @Test
    void 회원가입() {
       //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){     //예외 케이스 테스트하기.
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //assertThrows의 오른쪽이 실행되면 왼쪽의 오류가 터져야 함.(오류 객체 자체로 검증)
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //오류 메시지로 검증
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

       /*
        try{
            memberService.join(member1);
            fail();     //예외 케이스라서 try를 타면 안 되기 때문에 fail 발생시킨다.
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //오류가 나서 catch를 타야 예외 테스트 성공
        }
        */

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}