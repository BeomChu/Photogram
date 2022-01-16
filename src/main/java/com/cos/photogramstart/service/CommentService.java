package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.matcher.InheritedAnnotationMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    @Transactional
    public Comment 댓글쓰기 (String content, int imageId, int userId){

        //Tip (객체를 만들때 id값만 담아서 insert 할 수 있따
        //가짜 객체만들기 // 어차피 select할때 id값만 있으면됨
        //대신 return 시에 image 객체와 user개체는 id값만 가지고 있는 빈 객체를 리턴받는다.
        Image image = new Image();
        image.setId(imageId);

        //댓글에 username과 content, commentId가 필요하기때문에 user는 레퍼지토리에서 가져옴
        User user=userRepository.findById(userId).orElseThrow(()->{
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Transactional
    public void 댓글삭제(int id){
        try {
            commentRepository.deleteById(id);
        }catch (Exception e){
            throw new CustomApiException(e.getMessage());
        }
    }
}
