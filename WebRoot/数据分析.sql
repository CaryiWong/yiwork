/*根据兴趣ID分组，统计人数，查询兴趣名*/
select count(*) ,a.labels_id,b.zname  from ref_user_focus  a inner join labels b on b.id=a.labels_id  group by a. labels_id

/*1417415854485 创业  1417415854499环境于农业    7f7641397e3f45eebf7e1399107572747*/

select * from  ref_user_focus where labels_id='1417415854499' and user_id!='7f7641397e3f45eebf7e1399107572747'

select * from  ref_user_focus limit 1,20

select count(*) from ref_user_focus where user_id='0d74365abac54d29b3541397713028093'

select * from user inner join ref_user_focus on user.id=ref_user_focus.user_id  where user.minimg!='' ORDER BY RAND()  limit 1,100 



select * from userfocus where me_id='0d74365abac54d29b3541397713028093'

select *  from  ref_user_focus where labels_id='1417415854483' and user_id!='0d74365abac54d29b3541397713028093'
recommend

/*包括职业 */
 select * from user inner join ref_user_focus on user.id=ref_user_focus.user_id   
inner join ref_user_job on user.id=ref_user_job.user_id inner join  labels on labels.id=ref_user_job.labels_id
where user.minimg!='' ORDER BY RAND()  limit 1,100 



select distinct * from user inner join ref_user_focus on user.id=ref_user_focus.user_id 
inner join ref_user_job on user.id=ref_user_job.user_id inner join  labels on labels.id=ref_user_job.labels_id
where user.id='7f7641397e3f45eebf7e1399107572747'

---根据活动ID查询所有的报名人的缴费记录
select nickname from user where id in
(select user_id from order_table where business_id='14308919767134cfc4084b0c840409563' and paid_money=50) and root=2