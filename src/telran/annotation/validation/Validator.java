package telran.annotation.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import telran.annotation.validation.constraints.*;


@SuppressWarnings("unused")
public class Validator {
/**
 * validates the given object against the constraints in the package telran.annotation.validation.constraints
 * @param obj
 * @return list constraint violation messages or empty list if no violations
 */
	HashSet<Class<?>> clazzSet = new HashSet<>();
	public List<String> validate(Object obj) {
		List<String> res = new ArrayList<>();
		clazzSet.add(obj.getClass());
		
		if (obj == null) {
			res.add("Validated object can not be null");
			return res;
		}
		if (obj != null) {
			Field fields[] = obj.getClass().getDeclaredFields();
		for(Field field: fields) {
			field.setAccessible(true);
			for(Annotation annotation: field.getAnnotations()) {
				if(annotation.annotationType() == Valid.class) {
					try {
						Object nestedObj = field.get(obj);
						if (nestedObj != null && !clazzSet.contains(nestedObj.getClass())) {
							res.addAll(validate(nestedObj));
						}
						
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				processAnnotation(res, obj, annotation, field);
			}
		}
		}
		
		return res;
}
	
	private String max(Field field,  Object obj) {
		Max annotation = field.getAnnotation(Max.class);
		
		return maxMinValidation(field,  annotation,obj, true);
	}
	private String maxMinValidation(Field field, Annotation annotation, Object obj, boolean isMax) {
		String res="";
		try {
			
			double fieldValue = Double.parseDouble(field.get(obj).toString());
			if (isMax) {
				Max maxAnnotation = (Max) annotation;
				if (fieldValue > maxAnnotation.value()) {
					res = maxAnnotation.message();
				}
			} else {
				Min minAnnotation = (Min) annotation;
				if (fieldValue < minAnnotation.value()) {
					res = minAnnotation.message();
				}
			}
				
			
		} catch (Exception e) {
			res = "Wrong annotation with exception " + e.getMessage();
		}
		return res;
	}

	private String min(Field field,  Object obj) {
		Min annotation = field.getAnnotation(Min.class);
		return maxMinValidation(field, annotation, obj, false);
	}
	private String patern(Field field, Object obj) {
		Patern paternAnnotation = field.getAnnotation(Patern.class);
		String res = paternAnnotation.message();
		try {
			res =  ((String)field.get(obj)).matches(paternAnnotation.value()) ? "" : paternAnnotation.message();
		} catch (Exception e) {
			res = "Wrong annotation with exception " + e.getMessage();
		}
		return res;
	}
	private  void processAnnotation(List<String> errorMessages, Object obj, Annotation annotation, Field field) {
		String methodName = null;
		try {
			 methodName = annotation.annotationType().getSimpleName().toLowerCase();
			Method method = Validator.class.getDeclaredMethod(methodName, Field.class,  Object.class);
			method.setAccessible(true);
			String message = (String)method.invoke(this, field,  obj);
			if (!message.isEmpty()) {
				errorMessages.add(message);
			}
			
		}catch (Exception e) {
			System.out.printf("anootation %s is not a validation annotation\n", methodName);
		}
		
	}
}
