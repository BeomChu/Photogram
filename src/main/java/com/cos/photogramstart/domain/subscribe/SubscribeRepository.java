package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe,Integer> {

    @Modifying // INSERT,DELETE,UPDATE를 네이티브 쿼리로 작성하려면 해다 애노테이션 필요!
    @Query(value = "INSERT INTO subscribe(fromUserId,toUserId,createDate)    VALUES(:fromUserId,:toUserId,now())",nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId); //성공하면 1, 실패하면 -1이 리턴됨 //0이 나오면 변경이 안되었다는거임
                                                    //10개 성공하면 10, 마찬가지로 10개실피하면 -10
                                                    //성공한 갯수를 리턴받기 위해 int타입으로 받음

    @Modifying // INSERT,DELETE,UPDATE를 네이티브 쿼리로 작성하려면 해다 애노테이션 필요!
    @Query(value = "DELETE FROM subscribe WHERE fromUserId =:fromUserId AND toUserId= :toUserId",nativeQuery = true)
    void mUnSubscribe(int fromUserId, int toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId =:principalId AND toUserId =:pageUserId",nativeQuery = true)
    int mSubScribeState(int principalId,int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId =:pageUserId",nativeQuery = true)
    int mSubScribeCount(int pageUserId);



}
