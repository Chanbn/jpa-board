package com.board.domain.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.board.domain.comment.Comment;
import com.board.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "USERS")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
@SequenceGenerator (
	name = "BOARD_SEQ_GENERATOR",
    sequenceName = "BOARD_SEQ",	//매핑할 데이터 베이스 스퀀스 이름
    initialValue = 1, allocationSize=1)
public class Member implements Serializable{

	@Id
	@Column(name = "userid")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
	generator="BOARD_SEQ_GENERATOR")
	private Long id;
	
	@Column
	private String username;
	@Column
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column
	private String password;
	
	@Column
	private Boolean enabled;
	
	@Column
	private String nickname;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Builder.Default
	@OneToMany(mappedBy = "writer" ,cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Post> postList = new ArrayList<>();
	
	public void addPost(Post post) {
		postList.add(post);
		post.setWriter(this);
	}
	
	@JsonIgnoreProperties({"member", "parentComment"})
	@OneToMany(mappedBy = "writer")
	private List<Comment> comments = new ArrayList<>();
	
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setMember(this);
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

	public void addUserAuthority() {
        this.role = Role.USER;
    }
	
}
