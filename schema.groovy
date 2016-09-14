// LDBC Social Graph Schema script

// Node: Person
//       person_0_0.csv
//       id|firstName|lastName|gender|birthday|creationDate|locationIP|browserUsed
//       933|Mahinda|Perera|male|1989-12-03|2010-03-17T14:32:10.447+0000|192.248.2.123|Firefox
schema.propertyKey('id').Bigint().ifNotExists().create()
schema.propertyKey('firstName').Text().ifNotExists().create()
schema.propertyKey('lastName').Text().ifNotExists().create()
schema.propertyKey('gender').Text().ifNotExists().create()
schema.propertyKey('birthday').Timestamp().ifNotExists().create()
schema.propertyKey('creationDate').Timestamp().ifNotExists().create()
schema.propertyKey('locationIP').Text().ifNotExists().create()
schema.propertyKey('browserUsed').Text().ifNotExists().create()
schema.propertyKey('email').Text().multiple().ifNotExists().create()

// schema.vertexLabel("Person").partitionKey("id").create()
schema.vertexLabel("Person").properties("id","firstName","lastName","gender","birthday","creationDate","locationIP","browserUsed","email").create()
schema.vertexLabel("Person").index("PersonById").materialized().by("id").add()


// Node: Comment
//       comment_0_0.csv
//       id|creationDate|locationIP|browserUsed|content|length
//       1236950581249|2011-09-17T13:26:59.961+0000|77.240.75.197|Chrome|yes|3
schema.propertyKey('id').Bigint().ifNotExists().create()
schema.propertyKey('creationDate').Timestamp().ifNotExists().create()
schema.propertyKey('locationIP').Text().ifNotExists().create()
schema.propertyKey('browserUsed').Text().ifNotExists().create()
schema.propertyKey('content').Text().ifNotExists().create()
schema.propertyKey('length').Int().ifNotExists().create()

schema.vertexLabel("Comment").properties("id","creationDate","locationIP","browserUsed","content","length").create()
schema.vertexLabel("Comment").index("CommentById").materialized().by("id").add()


// Node: Forum
//       forum_0_0.csv
//       id|title|creationDate
//       0|Wall of Mahinda Perera|2010-03-17T14:32:20.447+0000
schema.propertyKey('id').Bigint().ifNotExists().create()
schema.propertyKey('title').Text().ifNotExists().create()
schema.propertyKey('creationDate').Timestamp().ifNotExists().create()

schema.vertexLabel("Forum").properties("id","title","creationDate").create()
schema.vertexLabel("Forum").index("ForumById").materialized().by("id").add()


// Node: Organisation
//       organisation_0_0.csv
//       id|type|name|url
//       5|company|Bakhtar_Afghan_Airlines|http://dbpedia.org/resource/Bakhtar_Afghan_Airlines
schema.propertyKey('id').Bigint().ifNotExists().create()
schema.propertyKey('type').Text().ifNotExists().create()
schema.propertyKey('name').Text().ifNotExists().create()
schema.propertyKey('url').Text().ifNotExists().create()

schema.vertexLabel("Organisation").properties("id","type","name","url").create()
schema.vertexLabel("Organisation").index("OrganisationById").materialized().by("id").add()


// Node: Place
//       place_0_0.csv
//       id|name|url|type
//       0|India|http://dbpedia.org/resource/India|country
schema.propertyKey('id').Bigint().ifNotExists().create()
schema.propertyKey('name').Text().ifNotExists().create()
schema.propertyKey('url').Text().ifNotExists().create()
schema.propertyKey('type').Text().ifNotExists().create()
schema.propertyKey('latLong').Point().ifNotExists().create()

schema.vertexLabel("Place").properties("id","name","url","type", "latLong").create()
schema.vertexLabel("Place").index("PlaceById").materialized().by("id").add()
schema.vertexLabel('Place').index('search').search().by('latLong').ifNotExists().add()


// Node: Post
//       post_0_0.csv
//       id|imageFile|creationDate|locationIP|browserUsed|language|content|length
//       1236950581248||2011-09-17T05:05:40.595+0000|192.248.2.123|Firefox|uz|About Augustine of Hippo, ustinian religious order; his memoriAbout Nicolas Sarkozy, y was also president of the General About Robert E. How|140
schema.propertyKey('id').Bigint().ifNotExists().create()
schema.propertyKey('imageFile').Text().ifNotExists().create()
schema.propertyKey('creationDate').Timestamp().ifNotExists().create()
schema.propertyKey('locationIP').Text().ifNotExists().create()
schema.propertyKey('browserUsed').Text().ifNotExists().create()
schema.propertyKey('language').Text().ifNotExists().create()
schema.propertyKey('content').Text().ifNotExists().create()
schema.propertyKey('length').Int().ifNotExists().create()

schema.vertexLabel("Post").properties("id","imageFile","creationDate","locationIP","browserUsed","language","content","length").create()
schema.vertexLabel("Post").index("PostById").materialized().by("id").add()
schema.vertexLabel("Post").index("PostByLanguage").secondary().by("language").add()


// Node: Tag
//       tag_0_0.csv
//       id|name|url
//       5|Mohammed_Zahir_Shah|http://dbpedia.org/resource/Mohammed_Zahir_Shah
schema.propertyKey('id').Bigint().ifNotExists().create()
schema.propertyKey('name').Text().ifNotExists().create()
schema.propertyKey('url').Text().ifNotExists().create()

schema.vertexLabel("Tag").properties("id","name","url").create()
schema.vertexLabel("Tag").index("TagById").materialized().by("id").add()


// Node: TagClass
//       tagclass_0_0.csv
//       id|name|url
//       0|Thing|http://www.w3.org/2002/07/owl#Thing
schema.propertyKey('id').Bigint().ifNotExists().create()
schema.propertyKey('name').Text().ifNotExists().create()
schema.propertyKey('url').Text().ifNotExists().create()

schema.vertexLabel("TagClass").properties("id","name","url").create()
schema.vertexLabel("TagClass").index("TagClassById").materialized().by("id").add()


// Edge: hasCreator
schema.edgeLabel('hasCreator').create()
schema.edgeLabel('hasCreator').connection('Comment','Person').add()
schema.edgeLabel('hasCreator').connection('Post','Person').add()

// Edge: hasTag
schema.edgeLabel('hasTag').create()
schema.edgeLabel('hasTag').connection('Forum','Tag').add()
schema.edgeLabel('hasTag').connection('Comment','Tag').add()
schema.edgeLabel('hasTag').connection('Post','Tag').add()

// Edge: isLocatedIn
schema.edgeLabel('isLocatedIn').create()
schema.edgeLabel('isLocatedIn').connection('Organisation','Place').add()
schema.edgeLabel('isLocatedIn').connection('Post','Place').add()
schema.edgeLabel('isLocatedIn').connection('Person', 'Place').add()
schema.edgeLabel('isLocatedIn').connection('Comment', 'Place').add()

// Edge: replyOf
schema.edgeLabel('replyOf').create()
schema.edgeLabel('replyOf').connection('Comment','Comment').add()
schema.edgeLabel('replyOf').connection('Comment','Post').add()

// Edge: containerOf
schema.edgeLabel('containerOf').connection('Forum','Post').create()

// Edge: hasMember
schema.propertyKey('joinDate').Timestamp().single().create()
schema.edgeLabel('hasMember').connection('Forum','Person').create()
schema.edgeLabel('hasMember').properties('joinDate').add()

// Edge: hasModerator
schema.edgeLabel('hasModerator').connection('Forum','Person').create()

// Edge: hasInterest
schema.edgeLabel('hasInterest').connection('Person','Tag').create()

// Edge: knows
schema.edgeLabel('knows').connection('Person','Person').create()

// Edge: likes
schema.edgeLabel('likes').create()
schema.edgeLabel('likes').connection('Person','Comment').add()
schema.edgeLabel('likes').connection('Person','Post').add()
schema.edgeLabel('likes').properties('creationDate').add()

// Edge: studyAt
schema.propertyKey('classYear').Text().single().create()
schema.edgeLabel('studyAt').connection('Person','Organisation').create()
schema.edgeLabel('studyAt').properties('classYear').add()

// Edge: workAt
schema.propertyKey('workFrom').Text().ifNotExists().create()
schema.edgeLabel('workAt').connection('Person','Organisation').create()
schema.edgeLabel('workAt').properties('workFrom').add()

// Edge: isPartOf
schema.edgeLabel('isPartOf').connection('Place','Place').create()

// Edge: isSubclassOf
schema.edgeLabel('isSubclassOf').connection('TagClass','TagClass').create()

// Edge: hasType
schema.edgeLabel('hasType').connection('Tag','TagClass').create()


// Language
schema.propertyKey('alpha2').Text().ifNotExists().create()
schema.propertyKey('English').Text().ifNotExists().create()

schema.vertexLabel("Language").properties("alpha2","English").create()
schema.vertexLabel("Language").index("LanguageByAlpha2").materialized().by("alpha2").add()


// Edge: speak
schema.edgeLabel('speaks').connection('Person','Language').create()
// Edge: writtenIn
schema.edgeLabel('writtenIn').connection('Post','Language').create()

