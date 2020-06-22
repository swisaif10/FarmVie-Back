package FarmVie.controller;

import java.util.Collection;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import FarmVie.model.Articles;
import FarmVie.model.Vote;
import FarmVie.repository.ArticleRep;
import FarmVie.repository.VoteRep;
import FarmVie.security.UserPrincipal;

@RestController
@RequestMapping("/vote")
public class VoteController {
	@Autowired
	VoteRep voteRep;
	@Autowired
	ArticleRep articleRep;
 
	
	@PostMapping("/add")

public void addvote(@RequestParam("ida") String ida2,@RequestParam("note") String note2) {
	long ida=Integer.parseInt(ida2);
	int note=Integer.parseInt(note2);
	long id=-1;
	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 if (principal instanceof UserPrincipal) {
	  id = ((UserPrincipal)principal).getId();
 
	 }
		Collection<Vote> ca=voteRep.findByUseridAndIdArticel(id, ida);
if(ca.size()==0) {
	 Vote vote=new Vote( ida, id, note);
	 voteRep.save(vote);}
else {
	for(Vote v :ca) {
v.setNote(note);
voteRep.save(v);}
}
	
	
	
	
	
}
	@GetMapping("/note/{ida}")
	public int getnote(@PathVariable("ida") String ida2 ) {
		long ida=Integer.parseInt(ida2);
		long id=-1;
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 if (principal instanceof UserPrincipal) {
		  id = ((UserPrincipal)principal).getId();
	 
		 }
		Collection<Vote> ca=voteRep.findByUseridAndIdArticel(id, ida);
		
	 if(ca.size()>0) {
			for(Vote v :ca)
				return v.getNote();}
 
		return 0;
	}
	

}
