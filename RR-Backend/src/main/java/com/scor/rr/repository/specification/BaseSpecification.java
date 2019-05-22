package com.scor.rr.repository.specification;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseSpecification<T, U> {

    private final String wildcard = "%";

    public abstract Specification<T> getFilter(U request);

    private String containsLowerCase(String searchField) {
        return  wildcard + searchField.toLowerCase() + wildcard;
    }
    protected String equals(String searchField) {
        return  searchField.toLowerCase();
    }

    protected String betweenDate(String searchField) {
        return  searchField.toLowerCase();
    }

    protected Specification<T> AttributeIn(SingularAttribute attribute, List<String> val) {
        return (root, query, cb) -> {
            if(val == null) return null;
            // if(val.isEmpty()) return cb.isNull(root.get(attribute));
            if(val.size()==0)  return null;
            return cb.lower(root.get(attribute)).in(val.stream().map(String::toLowerCase).collect(Collectors.toList()));
        };
    }

    protected Specification<T> AttributeEquals(SingularAttribute attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            return cb.equal(root.get(attribute), value);
        };
    }

    protected Specification<T> AttributeGreaterToEqual(SingularAttribute attribute, Double value){
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get(attribute),value );

        };
    }
    protected Specification<T> AttributeGreaterToEqual(SingularAttribute attribute, Integer value){
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get(attribute),value );

        };
    }
    protected Specification<T> AttributeEquals(SingularAttribute attribute, Integer value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            return cb.equal(root.get(attribute), value);
        };
    }
    protected Specification<T> AttributeEquals(SingularAttribute attribute, Object value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            return cb.equal(root.get(attribute), value);
        };
    }
    protected Specification<T> AttributeContains(SingularAttribute attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            return cb.like(cb.lower(root.get(attribute)),containsLowerCase(value));
        };
    }

    protected Specification<T> AttributeEquals(SingularAttribute attribute, Double value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            return cb.equal(cb.function("ceiling", Integer.class, root.get(attribute)), value);
        };
    }
    protected Specification<T> AttributeEquals(SingularAttribute attribute, Date value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value);
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            Predicate y = cb.equal(cb.function("year", Integer.class, root.get(attribute)), year);
            Predicate m = cb.equal(cb.function("month", Integer.class, root.get(attribute)), month+1); // month commence par 0
            Predicate d = cb.equal(cb.function("day", Integer.class, root.get(attribute)), day);
            return cb.and(y,m,d);
        };
    }

    protected Specification<T> dateGreaterThanOrEqualTo(SingularAttribute attribute, Date value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            return cb.greaterThanOrEqualTo(root.get(attribute), value);
        };
    }

    protected Specification<T> dateLessThanOrEqualTo(SingularAttribute attribute, Date value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            return cb.lessThanOrEqualTo(root.get(attribute), value);
        };
    }

    protected Specification<T> AttributeContainsBoolean(SingularAttribute attribute, String value) {
        return (root, query, cb) -> {
            Boolean b = BooleanUtils.toBooleanObject(value);
            if(b == null) { return null; }
            return cb.equal(root.get(attribute), b);
        };
    }

    protected Specification<T> AttributeContainsLong(SingularAttribute attribute, String value) {
        return (root, query, cb) -> {
            Long l;
            try{ l= Long.parseLong(value);
            }catch (Exception e){ return null; }
            return cb.equal(root.get(attribute), l);
        };
    }

    protected Specification<T> AttributeContainsInteger(SingularAttribute attribute, String value) {
        return (root, query, cb) -> {
            Integer year;
            try{ year=Integer.parseInt(value);
            }catch (Exception e){ return null; }
            return cb.equal(root.get(attribute), year);
        };
    }
}
