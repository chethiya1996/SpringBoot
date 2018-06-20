package abc.abc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import abc.abc.model.Comment;
import abc.abc.model.CommentRepository;

@RestController
@RequestMapping("/comments")
public class CommentService {
	
	@Autowired
	private CommentRepository repository;
	@RequestMapping(method=RequestMethod.POST)
	public void AddComment(@RequestBody Comment instance){
		try{
			repository.save(instance);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@SuppressWarnings("finally")
	@RequestMapping(method=RequestMethod.GET)
	public List<Comment> GetAllComments(){
		List<Comment> commentList=null;
		try {
			commentList=repository.findAll();
		} catch (Exception e) {		
			e.printStackTrace();
		}
		finally {
			return commentList;
		}
	}
	@SuppressWarnings("finally")
	private Comment findOne(String Id){
		Comment instance=null;
		try {
			List<Comment> commentList=repository.findAll();
			for (Comment comment : commentList) {
				if (comment.getId().equals(Id)) {
					instance=comment;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			return instance;
		}
	}
	@SuppressWarnings("finally")
	@RequestMapping(value="/{Id}",method=RequestMethod.GET)
	public Comment getSingle(@PathVariable String Id){
		Comment instance=null;
		try {
			instance=this.findOne(Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			return instance;
		}
	}
	@RequestMapping(value="/{Id}",method=RequestMethod.PUT)
	public void updateComment(@RequestBody Comment instance,@PathVariable String Id){
		try{
			Comment updateInstance=this.findOne(Id);
			updateInstance.setMessage(instance.getMessage());
			repository.save(updateInstance);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/{Id}",method=RequestMethod.DELETE)
	public void deleteComment(@PathVariable(value="id") String Id){
		try {
			repository.delete(this.findOne(Id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
