package com.deg2de.homepagests.dto;

/**
 * 클래스명(물리) : 작성용 댓글DTO
 * 클래스명(논리) : CommentWriteDto.java
 * 
 * 작성자 : 이성복
 * 최초 작성 날짜 : 2020-11-27
 * 마지막 수정 날짜 : 2020-11-27
 */
public class CommentWriteDto {
	
	/** 댓글 정보.댓글 게시글 번호 */
	private int commentTextNo;
	/** 댓글 정보.댓글 작성자 */
	private String commentId;
	/** 댓글 정보.댓글 내용 */
	private String commentDes;
	/** 댓글 정보.댓글 작성일 */
	private String commentWriteDate;
	
	public int getCommentTextNo() {
		return commentTextNo;
	}
	public void setCommentTextNo(int commentTextNo) {
		this.commentTextNo = commentTextNo;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentDes() {
		return commentDes;
	}
	public void setCommentDes(String commentDes) {
		this.commentDes = commentDes;
	}
	public String getCommentWriteDate() {
		return commentWriteDate;
	}
	public void setCommentWriteDate(String commentWriteDate) {
		this.commentWriteDate = commentWriteDate;
	}
}
