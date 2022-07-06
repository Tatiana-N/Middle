package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
	public static void main(String[] args) {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			Session session = sf.openSession();

			/*
			Candidate one = Candidate.of("Alex", "1", 100.8);
			Candidate two = Candidate.of("Nikolay", "8", 99.9);
			Candidate three = Candidate.of("Nikita", "5", 1000.0);
			session.save(one);
			session.save(two);
			session.save(three);
			*/
			
			/*
			1. SELECT
			 */
			System.out.println("1. SELECT");
			session.beginTransaction();
			Query query = session.createQuery("from Candidate");
			for (Object st : query.list()) {
				System.out.println(st);
			}
			Query queryId = session.createQuery("from Candidate s where s.id = 1");
			System.out.println(queryId.uniqueResult());
			
			Query queryIdModify = session.createQuery("from Candidate s where s.id = :fId");
			queryIdModify.setParameter("fId", 1);
			System.out.println(queryIdModify.uniqueResult());
			
			session.save(Candidate.of("Vasya", "6", 500.0));
			Query queryName = session.createQuery("from Candidate s where s.name = :fName");
			queryName.setParameter("fName", "Vasya");
			System.out.println(queryName.list());
			session.getTransaction().commit();
			/*
			2. UPDATE
			 */
			session.beginTransaction();
			System.out.println("2. UPDATE");
			Query queryUpdate = session.createQuery("update Candidate s set s.name = :newName, s.experience = :newExp where "
					+ "s.id = :fId");
			queryUpdate.setParameter("newName", "Tony");
			queryUpdate.setParameter("newExp", "25");
			queryUpdate.setParameter("fId", 1);
			queryUpdate.executeUpdate();
			for (Object st : query.list()) {
				System.out.println(st);
			}
			session.getTransaction().commit();
			/*
			3. DELETE
			 */
			session.beginTransaction();
			System.out.println("3. DELETE");
			session.createQuery("delete from Candidate where id = :fId").setParameter("fId", 2).executeUpdate();
			session.getTransaction().commit();
			for (Object st : query.list()) {
				System.out.println(st);
			}
			/*
			4. INSERT
			 */
			session.beginTransaction();
			System.out.println("4. INSERT");
			session.createQuery("insert into Candidate (name, experience, salary) "
					+ "select concat(s.name, 'NEW'), s.experience , s.salary + 10 "
					+ "from Candidate s where s.id = :fId")
					.setParameter("fId", 1)
					.executeUpdate();
			for (Object st : query.list()) {
				System.out.println(st);
			}
			session.getTransaction().commit();
			for (Object st : query.list()) {
				System.out.println(st);
			}
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
