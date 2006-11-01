package org.jboss.seam.util;

import java.lang.annotation.Annotation;

import javax.ejb.EJBContext;
import javax.naming.NamingException;


public class EJB
{
   public static final String EJBCONTEXT_NAME = "java:comp.ejb3/EJBContext";

   public @interface Dummy {}
   
   public static final Class<Annotation> STATELESS;
   public static final Class<Annotation> STATEFUL;
   public static final Class<Annotation> MESSAGE_DRIVEN;
   public static final Class<Annotation> PRE_PASSIVATE;
   public static final Class<Annotation> POST_ACTIVATE;
   public static final Class<Annotation> PRE_DESTROY;
   public static final Class<Annotation> POST_CONSTRUCT;
   public static final Class<Annotation> REMOTE;
   public static final Class<Annotation> REMOVE;
   public static final Class<Annotation> LOCAL;
   public static final Class<Annotation> APPLICATION_EXCEPTION;
   public static final Class<Annotation> INTERCEPTORS;
   public static final Class<Annotation> AROUND_INVOKE;
   public static boolean INVOCATION_CONTEXT_AVAILABLE;
   
   private static Class classForName(String name)
   {
      try
      {
         return Reflections.classForName(name);
      }
      catch (ClassNotFoundException cnfe)
      {
         return Dummy.class;
      }
   }
   
   static {
      STATELESS = classForName("javax.ejb.Stateless");
      STATEFUL = classForName("javax.ejb.Stateful");
      MESSAGE_DRIVEN = classForName("javax.ejb.MessageDriven");
      APPLICATION_EXCEPTION = classForName("javax.ejb.ApplicationException");
      REMOVE = classForName("javax.ejb.Remove");
      REMOTE = classForName("javax.ejb.Remote");
      LOCAL = classForName("javax.ejb.Local");
      PRE_PASSIVATE = classForName("javax.ejb.PrePassivate");
      POST_ACTIVATE = classForName("javax.ejb.PostActivate");
      PRE_DESTROY = classForName("javax.annotation.PreDestroy");
      POST_CONSTRUCT = classForName("javax.annotation.PostConstruct");
      INTERCEPTORS = classForName("javax.interceptor.Interceptors");
      AROUND_INVOKE = classForName("javax.interceptor.AroundInvoke");
      INVOCATION_CONTEXT_AVAILABLE = !classForName("javax.interceptor.InvocationContext").equals(Dummy.class);
   }
   
   public static String name(Annotation annotation)
   {
      return (String) Reflections.invokeAndWrap( Reflections.getMethod(annotation, "name"), annotation );
   }

   public static Class[] value(Annotation annotation)
   {
      return (Class[]) Reflections.invokeAndWrap( Reflections.getMethod(annotation, "value"), annotation );
   }
   
   public static boolean rollback(Annotation annotation)
   {
      return (Boolean) Reflections.invokeAndWrap( Reflections.getMethod(annotation, "rollback"), annotation );
   }

   public static EJBContext getEJBContext() throws NamingException
   {
      return (EJBContext) Naming.getInitialContext().lookup(EJBCONTEXT_NAME);
   }

}
