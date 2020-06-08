package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private CommentService commentService;

/*
    This controller method is called when the request pattern is of type '/image/{imageId}/{imageTitle}/comments' and also the incoming request is of POST type
    The method receives all the details of the comment to be stored in the database, and then the comment will be sent to the business logic to be persisted in the database
    Get the Image object from the imageId which is extracted from the URL and set it in the comment object
    Get the text of the comment from the URL and set it in the comment object
    Set the date of the comment
    Set the user of the image by getting the logged in user from the Http Session
    After setting all details, store the comment, and redirect to the showImage() method's mapping
*/

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@PathVariable("imageId") Integer imageId, @PathVariable("imageTitle") String imageTitle, @RequestParam(name = "comment") String commentText, Comment newComment, HttpSession session) {
        Image image = imageService.getImage(imageId);
        newComment.setImage(image);
        newComment.setCreatedDate(new Date());
        newComment.setText(commentText);
        User loggeduser = (User) session.getAttribute("loggeduser");
        newComment.setUser(loggeduser);
        commentService.addComment(newComment);
        return "redirect:/images/" + imageId + "/" + imageTitle;
    }
}
