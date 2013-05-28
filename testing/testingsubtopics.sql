select 3000 as EXPRECTED_TOTAL, t.total as ALL_RECORDS ,  (3000 - t.total) as MISSING, t2.total as RECORDS_WITH_TEXT ,t.total - t2.total as EMPTY_RECORDS, ROUND((CAST( t2.total as DECIMAL)/3000)*100) as success_rate , ROUND((CAST((3000 - t2.total) as DECIMAL)/3000)*100) as error_rate  from ( select count(*) as total from subtopics ) as t, ( select count(*) as total from subtopics WHERE display_name <> '' ) as t2


--select * from subtopics
--delete from subtopics