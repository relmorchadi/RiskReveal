package com.scor.rr.repository.specification;

import com.scor.rr.domain.enums.NumberOperator;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseSpecification<T, U> {

    private final String wildcard = "%";

    public abstract Specification<T> getData(U request);

    private String containsLowerCase(String searchField) {
        return  wildcard + searchField.toLowerCase() + wildcard;
    }

    protected String equals(String searchField) {
        return  searchField.toLowerCase();
    }

    protected String betweenDate(String searchField) {
        return  searchField.toLowerCase();
    }

    protected Specification<T> AttributeIsNull(SingularAttribute attribute) {
        return (root, query, cb) -> cb.isNull(root.get(attribute));
    }

    protected Specification<T> AttributeIsNotNull(SingularAttribute attribute) {
        return (root, query, cb) -> cb.isNotNull(root.get(attribute));
    }

    protected Specification<T> AttributeEquals(SingularAttribute attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) { return null; }
            return cb.equal(root.get(attribute), value);
        };
    }

    protected Specification<T> AttributeGreaterToEqual(SingularAttribute attribute, Float value){
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get(attribute),value );

        };
    }

    protected Specification<T> AttributeLessThan(SingularAttribute attribute, Double value){
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }
            return cb.lessThan(root.get(attribute),value );

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

    protected Specification<T> AttributeIsLike(SingularAttribute<T, ?> attribute, String keyword) {
        return (root, query, cb) -> cb.like(root.get(attribute).as(String.class), "%" + keyword + "%");
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

    protected Specification<T> AttributeBoolean(SingularAttribute attribute, Object value) {
        return (root, query, cb) -> {
            String s = (String) value;
            Boolean b = BooleanUtils.toBooleanObject(s);
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

    protected Specification<T> AttributeInLong(SingularAttribute<T, Long> attribute, List<Long> l) {
        return (root, query, cb) -> {
            if(l == null) return null;
            // if(val.isEmpty()) return cb.isNull(root.get(attribute));
            if(l.size()==0)  return null;
            CriteriaBuilder.In<Long> inClause = cb.in(root.get(attribute));
            for(Long el : l) inClause.value(el);
            return inClause;
        };
    }

    protected Specification<T> AttributeIn(SingularAttribute attribute, List<String> val) {
        return (root, query, cb) -> {
            if(val == null) return null;
            // if(val.isEmpty()) return cb.isNull(root.get(attribute));
            if(val.size()==0)  return null;
            return cb.lower(root.get(attribute)).in(val.stream().map(String::toLowerCase).collect(Collectors.toList()));
        };
    }

    protected Specification<T> AttributeNumber(SingularAttribute attribute, Double number, NumberOperator operator) {
        //NumberHelper number = new NumberHelper(numberFilter.getValue());
        if(operator == null) return null;

        switch (operator.getNumberOperator()) {
            case "EQUAL":
                return this.AttributeEquals(attribute, number);
            case "LESS THAN":
                return this.AttributeLessThan(attribute, number);
            default:
                return null;
        }
    }
}
