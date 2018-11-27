# Stubs for pandas.core.frame (Python 3.6)
#
# NOTE: This dynamically typed stub was automatically generated by stubgen.

from pandas.core.generic import NDFrame
from typing import Any, Optional

class DataFrame(NDFrame):
    def __init__(self, data: Optional[Any] = ..., index: Optional[Any] = ..., columns: Optional[Any] = ..., dtype: Optional[Any] = ..., copy: bool = ...) -> None: ...
    @property
    def axes(self): ...
    @property
    def shape(self): ...
    def __unicode__(self): ...
    @property
    def style(self): ...
    def iteritems(self) -> None: ...
    def iterrows(self) -> None: ...
    def itertuples(self, index: bool = ..., name: str = ...): ...
    items: Any = ...
    def __len__(self): ...
    def dot(self, other: Any): ...
    def __matmul__(self, other: Any): ...
    def __rmatmul__(self, other: Any): ...
    @classmethod
    def from_dict(cls, data: Any, orient: str = ..., dtype: Optional[Any] = ..., columns: Optional[Any] = ...): ...
    def to_dict(self, orient: str = ..., into: Any = ...): ...
    def to_gbq(self, destination_table: Any, project_id: Optional[Any] = ..., chunksize: Optional[Any] = ..., reauth: bool = ..., if_exists: str = ..., private_key: Optional[Any] = ..., auth_local_webserver: bool = ..., table_schema: Optional[Any] = ..., location: Optional[Any] = ..., progress_bar: bool = ..., verbose: Optional[Any] = ...): ...
    @classmethod
    def from_records(cls, data: Any, index: Optional[Any] = ..., exclude: Optional[Any] = ..., columns: Optional[Any] = ..., coerce_float: bool = ..., nrows: Optional[Any] = ...): ...
    def to_records(self, index: bool = ..., convert_datetime64: Optional[Any] = ...): ...
    @classmethod
    def from_items(cls, items: Any, columns: Optional[Any] = ..., orient: str = ...): ...
    @classmethod
    def from_csv(cls, path: Any, header: int = ..., sep: str = ..., index_col: int = ..., parse_dates: bool = ..., encoding: Optional[Any] = ..., tupleize_cols: Optional[Any] = ..., infer_datetime_format: bool = ...): ...
    def to_sparse(self, fill_value: Optional[Any] = ..., kind: str = ...): ...
    def to_panel(self): ...
    def to_excel(self, excel_writer: Any, sheet_name: str = ..., na_rep: str = ..., float_format: Optional[Any] = ..., columns: Optional[Any] = ..., header: bool = ..., index: bool = ..., index_label: Optional[Any] = ..., startrow: int = ..., startcol: int = ..., engine: Optional[Any] = ..., merge_cells: bool = ..., encoding: Optional[Any] = ..., inf_rep: str = ..., verbose: bool = ..., freeze_panes: Optional[Any] = ...) -> None: ...
    def to_stata(self, fname: Any, convert_dates: Optional[Any] = ..., write_index: bool = ..., encoding: str = ..., byteorder: Optional[Any] = ..., time_stamp: Optional[Any] = ..., data_label: Optional[Any] = ..., variable_labels: Optional[Any] = ..., version: int = ..., convert_strl: Optional[Any] = ...) -> None: ...
    def to_feather(self, fname: Any) -> None: ...
    def to_parquet(self, fname: Any, engine: str = ..., compression: str = ..., index: Optional[Any] = ..., **kwargs: Any) -> None: ...
    def to_string(self, buf: Optional[Any] = ..., columns: Optional[Any] = ..., col_space: Optional[Any] = ..., header: bool = ..., index: bool = ..., na_rep: str = ..., formatters: Optional[Any] = ..., float_format: Optional[Any] = ..., sparsify: Optional[Any] = ..., index_names: bool = ..., justify: Optional[Any] = ..., line_width: Optional[Any] = ..., max_rows: Optional[Any] = ..., max_cols: Optional[Any] = ..., show_dimensions: bool = ...): ...
    def to_html(self, buf: Optional[Any] = ..., columns: Optional[Any] = ..., col_space: Optional[Any] = ..., header: bool = ..., index: bool = ..., na_rep: str = ..., formatters: Optional[Any] = ..., float_format: Optional[Any] = ..., sparsify: Optional[Any] = ..., index_names: bool = ..., justify: Optional[Any] = ..., bold_rows: bool = ..., classes: Optional[Any] = ..., escape: bool = ..., max_rows: Optional[Any] = ..., max_cols: Optional[Any] = ..., show_dimensions: bool = ..., notebook: bool = ..., decimal: str = ..., border: Optional[Any] = ..., table_id: Optional[Any] = ...): ...
    def info(self, verbose: Optional[Any] = ..., buf: Optional[Any] = ..., max_cols: Optional[Any] = ..., memory_usage: Optional[Any] = ..., null_counts: Optional[Any] = ...): ...
    def memory_usage(self, index: bool = ..., deep: bool = ...): ...
    def transpose(self, *args: Any, **kwargs: Any): ...
    T: Any = ...
    def get_value(self, index: Any, col: Any, takeable: bool = ...): ...
    def set_value(self, index: Any, col: Any, value: Any, takeable: bool = ...): ...
    def __getitem__(self, key: Any): ...
    def query(self, expr: Any, inplace: bool = ..., **kwargs: Any): ...
    def eval(self, expr: Any, inplace: bool = ..., **kwargs: Any): ...
    def select_dtypes(self, include: Optional[Any] = ..., exclude: Optional[Any] = ...): ...
    def __setitem__(self, key: Any, value: Any): ...
    def insert(self, loc: Any, column: Any, value: Any, allow_duplicates: bool = ...) -> None: ...
    def assign(self, **kwargs: Any): ...
    def lookup(self, row_labels: Any, col_labels: Any): ...
    def align(self, other: Any, join: str = ..., axis: Optional[Any] = ..., level: Optional[Any] = ..., copy: bool = ..., fill_value: Optional[Any] = ..., method: Optional[Any] = ..., limit: Optional[Any] = ..., fill_axis: int = ..., broadcast_axis: Optional[Any] = ...): ...
    def reindex(self, *args: Any, **kwargs: Any): ...
    def reindex_axis(self, labels: Any, axis: int = ..., method: Optional[Any] = ..., level: Optional[Any] = ..., copy: bool = ..., limit: Optional[Any] = ..., fill_value: Any = ...): ...
    def drop(self, labels: Optional[Any] = ..., axis: int = ..., index: Optional[Any] = ..., columns: Optional[Any] = ..., level: Optional[Any] = ..., inplace: bool = ..., errors: str = ...): ...
    def rename(self, *args: Any, **kwargs: Any): ...
    def fillna(self, value: Optional[Any] = ..., method: Optional[Any] = ..., axis: Optional[Any] = ..., inplace: bool = ..., limit: Optional[Any] = ..., downcast: Optional[Any] = ..., **kwargs: Any): ...
    def replace(self, to_replace: Optional[Any] = ..., value: Optional[Any] = ..., inplace: bool = ..., limit: Optional[Any] = ..., regex: bool = ..., method: str = ...): ...
    def shift(self, periods: int = ..., freq: Optional[Any] = ..., axis: int = ...): ...
    def set_index(self, keys: Any, drop: bool = ..., append: bool = ..., inplace: bool = ..., verify_integrity: bool = ...): ...
    def reset_index(self, level: Optional[Any] = ..., drop: bool = ..., inplace: bool = ..., col_level: int = ..., col_fill: str = ...): ...
    def isna(self): ...
    def isnull(self): ...
    def notna(self): ...
    def notnull(self): ...
    def dropna(self, axis: int = ..., how: str = ..., thresh: Optional[Any] = ..., subset: Optional[Any] = ..., inplace: bool = ...): ...
    def drop_duplicates(self, subset: Optional[Any] = ..., keep: str = ..., inplace: bool = ...): ...
    def duplicated(self, subset: Optional[Any] = ..., keep: str = ...): ...
    def sort_values(self, by: Optional[Any] = ..., axis: int = ..., ascending: Any = ..., inplace: Any = ..., kind: str = ..., na_position: str = ...) -> None: ...
    def sort_index(self, axis: int = ..., level: Optional[Any] = ..., ascending: bool = ..., inplace: bool = ..., kind: str = ..., na_position: str = ..., sort_remaining: bool = ..., by: Optional[Any] = ...): ...
    def nlargest(self, n: Any, columns: Any, keep: str = ...): ...
    def nsmallest(self, n: Any, columns: Any, keep: str = ...): ...
    def swaplevel(self, i: int = ..., j: int = ..., axis: int = ...): ...
    def reorder_levels(self, order: Any, axis: int = ...): ...
    def combine(self, other: Any, func: Any, fill_value: Optional[Any] = ..., overwrite: bool = ...): ...
    def combine_first(self, other: Any): ...
    def update(self, other: Any, join: str = ..., overwrite: bool = ..., filter_func: Optional[Any] = ..., raise_conflict: bool = ...) -> None: ...
    def pivot(self, index: Optional[Any] = ..., columns: Optional[Any] = ..., values: Optional[Any] = ...): ...
    def pivot_table(self, values: Optional[Any] = ..., index: Optional[Any] = ..., columns: Optional[Any] = ..., aggfunc: str = ..., fill_value: Optional[Any] = ..., margins: bool = ..., dropna: bool = ..., margins_name: str = ...): ...
    def stack(self, level: int = ..., dropna: bool = ...): ...
    def unstack(self, level: int = ..., fill_value: Optional[Any] = ...): ...
    def melt(self, id_vars: Optional[Any] = ..., value_vars: Optional[Any] = ..., var_name: Optional[Any] = ..., value_name: str = ..., col_level: Optional[Any] = ...): ...
    def diff(self, periods: int = ..., axis: int = ...): ...
    def aggregate(self, func: Any, axis: int = ..., *args: Any, **kwargs: Any): ...
    agg: Any = ...
    def transform(self, func: Any, axis: int = ..., *args: Any, **kwargs: Any): ...
    def apply(self, func: Any, axis: int = ..., broadcast: Optional[Any] = ..., raw: bool = ..., reduce: Optional[Any] = ..., result_type: Optional[Any] = ..., args: Any = ..., **kwds: Any): ...
    def applymap(self, func: Any): ...
    def append(self, other: Any, ignore_index: bool = ..., verify_integrity: bool = ..., sort: Optional[Any] = ...): ...
    def join(self, other: Any, on: Optional[Any] = ..., how: str = ..., lsuffix: str = ..., rsuffix: str = ..., sort: bool = ...): ...
    def merge(self, right: Any, how: str = ..., on: Optional[Any] = ..., left_on: Optional[Any] = ..., right_on: Optional[Any] = ..., left_index: bool = ..., right_index: bool = ..., sort: bool = ..., suffixes: Any = ..., copy: bool = ..., indicator: bool = ..., validate: Optional[Any] = ...): ...
    def round(self, decimals: int = ..., *args: Any, **kwargs: Any): ...
    def corr(self, method: str = ..., min_periods: int = ...): ...
    def cov(self, min_periods: Optional[Any] = ...): ...
    def corrwith(self, other: Any, axis: int = ..., drop: bool = ...): ...
    def count(self, axis: int = ..., level: Optional[Any] = ..., numeric_only: bool = ...): ...
    def nunique(self, axis: int = ..., dropna: bool = ...): ...
    def idxmin(self, axis: int = ..., skipna: bool = ...): ...
    def idxmax(self, axis: int = ..., skipna: bool = ...): ...
    def mode(self, axis: int = ..., numeric_only: bool = ..., dropna: bool = ...): ...
    def quantile(self, q: float = ..., axis: int = ..., numeric_only: bool = ..., interpolation: str = ...): ...
    def to_timestamp(self, freq: Optional[Any] = ..., how: str = ..., axis: int = ..., copy: bool = ...): ...
    def to_period(self, freq: Optional[Any] = ..., axis: int = ..., copy: bool = ...): ...
    def isin(self, values: Any): ...
    plot: Any = ...
    hist: Any = ...
    boxplot: Any = ...

def extract_index(data: Any): ...
