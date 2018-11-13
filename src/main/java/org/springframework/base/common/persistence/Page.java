/*     */ package org.springframework.base.common.persistence;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class Page<T>
/*     */ {
/*     */   public static final String ASC = "asc";
/*     */   public static final String DESC = "desc";
/*  29 */   protected int pageNo = 1;
/*  30 */   protected int pageSize = -1;
/*  31 */   protected String orderBy = null;
/*  32 */   protected String order = null;
/*  33 */   protected boolean autoCount = true;
/*     */ 
/*  36 */   protected List<T> result = Lists.newArrayList();
/*  37 */   protected long totalCount = -1L;
/*     */   protected String columns;
/*     */   protected String primaryKey;
/*     */   protected String tableName;
/*     */ 
/*     */   public Page()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Page(int pageSize)
/*     */   {
/*  48 */     this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */   public Page(int pageNo, int pageSize, String orderBy, String order) {
/*  52 */     this.pageNo = pageNo;
/*  53 */     this.pageSize = pageSize;
/*  54 */     this.orderBy = orderBy;
/*  55 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String getColumns()
/*     */   {
/*  68 */     return this.columns;
/*     */   }
/*     */ 
/*     */   public void setColumns(String columns) {
/*  72 */     this.columns = columns;
/*     */   }
/*     */ 
/*     */   public String getPrimaryKey() {
/*  76 */     return this.primaryKey;
/*     */   }
/*     */ 
/*     */   public void setPrimaryKey(String primaryKey) {
/*  80 */     this.primaryKey = primaryKey;
/*     */   }
/*     */ 
/*     */   public String getTableName() {
/*  84 */     return this.tableName;
/*     */   }
/*     */ 
/*     */   public void setTableName(String tableName) {
/*  88 */     this.tableName = tableName;
/*     */   }
/*     */ 
/*     */   public int getPageNo()
/*     */   {
/*  97 */     return this.pageNo;
/*     */   }
/*     */ 
/*     */   public void setPageNo(int pageNo)
/*     */   {
/* 104 */     this.pageNo = pageNo;
/*     */ 
/* 106 */     if (pageNo < 1)
/* 107 */       this.pageNo = 1;
/*     */   }
/*     */ 
/*     */   public Page<T> pageNo(int thePageNo)
/*     */   {
/* 115 */     setPageNo(thePageNo);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/* 123 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public void setPageSize(int pageSize)
/*     */   {
/* 130 */     this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */   public Page<T> pageSize(int thePageSize)
/*     */   {
/* 137 */     setPageSize(thePageSize);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public int getFirst()
/*     */   {
/* 145 */     return (this.pageNo - 1) * this.pageSize + 1;
/*     */   }
/*     */ 
/*     */   public String getOrderBy()
/*     */   {
/* 152 */     return this.orderBy;
/*     */   }
/*     */ 
/*     */   public void setOrderBy(String orderBy)
/*     */   {
/* 159 */     this.orderBy = orderBy;
/*     */   }
/*     */ 
/*     */   public Page<T> orderBy(String theOrderBy)
/*     */   {
/* 166 */     setOrderBy(theOrderBy);
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public String getOrder()
/*     */   {
/* 174 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order)
/*     */   {
/* 183 */     String lowcaseOrder = StringUtils.lowerCase(order);
/*     */ 
/* 186 */     String[] orders = StringUtils.split(lowcaseOrder, ',');
/* 187 */     for (String orderStr : orders) {
/* 188 */       if ((!StringUtils.equals("desc", orderStr)) && (!StringUtils.equals("asc", orderStr))) {
/* 189 */         throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
/*     */       }
/*     */     }
/*     */ 
/* 193 */     this.order = lowcaseOrder;
/*     */   }
/*     */ 
/*     */   public Page<T> order(String theOrder)
/*     */   {
/* 200 */     setOrder(theOrder);
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isOrderBySetted()
/*     */   {
/* 208 */     return (StringUtils.isNotBlank(this.orderBy)) && (StringUtils.isNotBlank(this.order));
/*     */   }
/*     */ 
/*     */   public boolean isAutoCount()
/*     */   {
/* 215 */     return this.autoCount;
/*     */   }
/*     */ 
/*     */   public void setAutoCount(boolean autoCount)
/*     */   {
/* 222 */     this.autoCount = autoCount;
/*     */   }
/*     */ 
/*     */   public Page<T> autoCount(boolean theAutoCount)
/*     */   {
/* 229 */     setAutoCount(theAutoCount);
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */   public List<T> getResult()
/*     */   {
/* 239 */     return this.result;
/*     */   }
/*     */ 
/*     */   public void setResult(List<T> result)
/*     */   {
/* 246 */     this.result = result;
/*     */   }
/*     */ 
/*     */   public long getTotalCount()
/*     */   {
/* 253 */     return this.totalCount;
/*     */   }
/*     */ 
/*     */   public void setTotalCount(long totalCount)
/*     */   {
/* 260 */     this.totalCount = totalCount;
/*     */   }
/*     */ 
/*     */   public long getTotalPages()
/*     */   {
/* 267 */     if (this.totalCount < 0L) {
/* 268 */       return -1L;
/*     */     }
/*     */ 
/* 271 */     long count = this.totalCount / this.pageSize;
/* 272 */     if (this.totalCount % this.pageSize > 0L) {
/* 273 */       count += 1L;
/*     */     }
/* 275 */     return count;
/*     */   }
/*     */ 
/*     */   public boolean isHasNext()
/*     */   {
/* 283 */     return this.pageNo + 1 <= getTotalPages();
/*     */   }
/*     */ 
/*     */   public int getNextPage()
/*     */   {
/* 291 */     if (isHasNext()) {
/* 292 */       return this.pageNo + 1;
/*     */     }
/* 294 */     return this.pageNo;
/*     */   }
/*     */ 
/*     */   public boolean isHasPre()
/*     */   {
/* 302 */     return this.pageNo - 1 >= 1;
/*     */   }
/*     */ 
/*     */   public int getPrePage()
/*     */   {
/* 310 */     if (isHasPre()) {
/* 311 */       return this.pageNo - 1;
/*     */     }
/* 313 */     return this.pageNo;
/*     */   }
/*     */ }

/* Location:           C:\Users\renfei\Desktop\treeDMS-2.3.1\treeDMS-2.3.1\webapps\treesoft\WEB-INF\classes\
 * Qualified Name:     org.springframework.base.common.persistence.Page
 * JD-Core Version:    0.6.0
 */