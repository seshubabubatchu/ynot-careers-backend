model - entity
control flow
controller -> service -> repository

category and job model

parent - category (since it holds list of jobs)
child - job

owner - job (since it have column key)

cascade specified in parent removes the child entities

orphan removal is used in parent entity only,
if we take example of category and job models here, category is the parent if we specifiy orphan removal true in category then if we remove a job from array list then it is automatically deleted from the db

@JsonIgnore - ignore entire field
@JsonIgnoreProperties - ignore particular property
