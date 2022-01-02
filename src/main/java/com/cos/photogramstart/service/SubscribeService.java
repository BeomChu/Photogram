package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;  // Repository는 EntityManager를 구현해서 만들어져있는 구현체

    @Transactional(readOnly = true)
    public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){

        StringBuffer sb = new StringBuffer();

        //쿼리준비하는거
        sb.append("SELECT u.id, u.username, u.profileImageUrl, "); // 띄워쓰기해야함
        sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id),1,0) subscribeState, ");
        sb.append("if((?= u.id),1,0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ? "); //세미콜론 첨부하면 안됨

        // 1.물음표 principalId
        // 2.물음표 principalId
        // 3.마지막 물음표 pageUserId

        //쿼리 완성하는거
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1,principalId)
                .setParameter(2,principalId)
                .setParameter(3,pageUserId);

        //쿼리 실행 (qlrm 라이브러리 필요 = DTO에 DB결과를 리턴하기 위해)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query,SubscribeDto.class); // 하나 받으려면 unique , 리스트는 리스트
        return subscribeDtos;
    }

    @Transactional
    public void 구독하기(int fromUserId, int toUserId){
        subscribeRepository.mSubscribe(fromUserId, toUserId); //m은 내가 만들었다는 약어임
                                                                            //( 메서드를 커스텀함)
    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }

}
