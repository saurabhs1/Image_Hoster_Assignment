package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.Comment;
import ImageHoster.model.User;
import ImageHoster.service.ImageService;
import ImageHoster.service.CommentService;
import ImageHoster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.time.LocalDate;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    //This method is called when the user submits the comment form
    //It populates the required fields and calls the
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComment(@PathVariable("imageId") Integer imageId, @PathVariable("imageTitle") String title, @RequestParam("comment") String text, HttpSession session) {
        Image image = imageService.getImage(imageId);
        User currentUser = (User)session.getAttribute("loggeduser");

        Comment comment = new Comment();
        comment.setText(text);
        comment.setImage(image);
        comment.setUser(currentUser);
        comment.setCreatedDate(LocalDate.now());

        commentService.createComment(comment);
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }

}
