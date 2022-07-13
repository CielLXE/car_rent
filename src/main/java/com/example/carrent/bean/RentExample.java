package com.example.carrent.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RentExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCarIdIsNull() {
            addCriterion("car_id is null");
            return (Criteria) this;
        }

        public Criteria andCarIdIsNotNull() {
            addCriterion("car_id is not null");
            return (Criteria) this;
        }

        public Criteria andCarIdEqualTo(Integer value) {
            addCriterion("car_id =", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotEqualTo(Integer value) {
            addCriterion("car_id <>", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdGreaterThan(Integer value) {
            addCriterion("car_id >", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("car_id >=", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdLessThan(Integer value) {
            addCriterion("car_id <", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdLessThanOrEqualTo(Integer value) {
            addCriterion("car_id <=", value, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdIn(List<Integer> values) {
            addCriterion("car_id in", values, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotIn(List<Integer> values) {
            addCriterion("car_id not in", values, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdBetween(Integer value1, Integer value2) {
            addCriterion("car_id between", value1, value2, "carId");
            return (Criteria) this;
        }

        public Criteria andCarIdNotBetween(Integer value1, Integer value2) {
            addCriterion("car_id not between", value1, value2, "carId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andRentDateIsNull() {
            addCriterion("rent_date is null");
            return (Criteria) this;
        }

        public Criteria andRentDateIsNotNull() {
            addCriterion("rent_date is not null");
            return (Criteria) this;
        }

        public Criteria andRentDateEqualTo(Date value) {
            addCriterionForJDBCDate("rent_date =", value, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("rent_date <>", value, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateGreaterThan(Date value) {
            addCriterionForJDBCDate("rent_date >", value, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("rent_date >=", value, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateLessThan(Date value) {
            addCriterionForJDBCDate("rent_date <", value, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("rent_date <=", value, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateIn(List<Date> values) {
            addCriterionForJDBCDate("rent_date in", values, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("rent_date not in", values, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("rent_date between", value1, value2, "rentDate");
            return (Criteria) this;
        }

        public Criteria andRentDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("rent_date not between", value1, value2, "rentDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateIsNull() {
            addCriterion("supposed_return_date is null");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateIsNotNull() {
            addCriterion("supposed_return_date is not null");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateEqualTo(Date value) {
            addCriterionForJDBCDate("supposed_return_date =", value, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("supposed_return_date <>", value, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateGreaterThan(Date value) {
            addCriterionForJDBCDate("supposed_return_date >", value, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("supposed_return_date >=", value, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateLessThan(Date value) {
            addCriterionForJDBCDate("supposed_return_date <", value, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("supposed_return_date <=", value, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateIn(List<Date> values) {
            addCriterionForJDBCDate("supposed_return_date in", values, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("supposed_return_date not in", values, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("supposed_return_date between", value1, value2, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andSupposedReturnDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("supposed_return_date not between", value1, value2, "supposedReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateIsNull() {
            addCriterion("actual_return_date is null");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateIsNotNull() {
            addCriterion("actual_return_date is not null");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateEqualTo(Date value) {
            addCriterionForJDBCDate("actual_return_date =", value, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("actual_return_date <>", value, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateGreaterThan(Date value) {
            addCriterionForJDBCDate("actual_return_date >", value, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_return_date >=", value, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateLessThan(Date value) {
            addCriterionForJDBCDate("actual_return_date <", value, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("actual_return_date <=", value, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateIn(List<Date> values) {
            addCriterionForJDBCDate("actual_return_date in", values, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("actual_return_date not in", values, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_return_date between", value1, value2, "actualReturnDate");
            return (Criteria) this;
        }

        public Criteria andActualReturnDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("actual_return_date not between", value1, value2, "actualReturnDate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}