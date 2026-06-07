

import zipfile
zip_ref = zipfile.ZipFile('/content/archive (20).zip','r')


zip_ref.extractall('/content')
zip_ref.close()

import pandas as pd
category_df=pd.read_csv('/content/amazon_categories.csv')
category_df.head()

category_df.info()

category_df.shape

import pandas as pd
Product_df=pd.read_csv('/content/amazon_products.csv')
Product_df.head()

Product_df.shape

Product_df.describe()

Product_df.info()

Product_df[['title','category_id','stars','boughtInLastMonth']].head()

sample_df = Product_df.sample(250000, random_state=42)

sample_df.head()

sample_df['category_id'].nunique()

category_df.head()

sample_df = sample_df.merge(
    category_df,
    left_on='category_id',
    right_on='id',
    how='left'
)

sample_df[['title','category_name']].head()

sample_df['features'] = (
    sample_df['title'].fillna('') + ' ' +
    sample_df['title'].fillna('') + ' ' +
    sample_df['title'].fillna('') + ' ' +
    sample_df['category_name'].fillna('')
)

from sklearn.feature_extraction.text import TfidfVectorizer

tfidf = TfidfVectorizer(
    stop_words='english',
    max_features=10000
)

tfidf_matrix = tfidf.fit_transform(sample_df['features'])

print(tfidf_matrix.shape)

from sklearn.metrics.pairwise import linear_kernel
import numpy as np
def recommend(product_title, top_n=10):

    idx_list = sample_df[
        sample_df['title'].str.contains(
            product_title,
            case=False,
            na=False
        )
    ].index

    if len(idx_list) == 0:
        return "Product not found"

    idx = idx_list[0]

    cosine_scores = linear_kernel(
        tfidf_matrix[idx:idx+1],
        tfidf_matrix
    ).flatten()

    similar_indices = cosine_scores.argsort()[-top_n-1:-1][::-1]
    similar_products = sample_df.iloc[similar_indices].copy()
    similar_products['score'] = (
    similar_products['stars'] * 0.4 +
    np.log1p(similar_products['boughtInLastMonth']) * 0.6
)
    similar_products = similar_products.sort_values(
    by='score',
    ascending=False
)

    return sample_df.iloc[similar_indices][
        ['title','category_name','stars','boughtInLastMonth']
    ]

recommend("Jitsu")

sample_df.sample(3)[['title']]

recommend("Motorcycle Helmet")

import joblib

joblib.dump(tfidf, 'tfidf_vectorizer.pkl')
joblib.dump(tfidf_matrix, 'tfidf_matrix.pkl')
joblib.dump(sample_df, 'products_df.pkl')

import os

print(os.listdir())

loaded_tfidf = joblib.load('tfidf_vectorizer.pkl')
loaded_matrix = joblib.load('tfidf_matrix.pkl')
loaded_df = joblib.load('products_df.pkl')

print(type(loaded_tfidf))
print(type(loaded_matrix))
print(type(loaded_df))

import os

for f in [
    'tfidf_vectorizer.pkl',
    'tfidf_matrix.pkl',
    'products_df.pkl'
]:
    print(f, round(os.path.getsize(f)/(1024*1024),2), "MB")

import joblib

tfidf = joblib.load('tfidf_vectorizer.pkl')
tfidf_matrix = joblib.load('tfidf_matrix.pkl')
products_df = joblib.load('products_df.pkl')

print("Loaded Successfully")

from sklearn.metrics.pairwise import linear_kernel
import numpy as np

def recommend(product_title, top_n=10):

    idx_list = products_df[
        products_df['title'].str.contains(
            product_title,
            case=False,
            na=False
        )
    ].index

    if len(idx_list) == 0:
        return "Product not found"

    idx = idx_list[0]

    cosine_scores = linear_kernel(
        tfidf_matrix[idx:idx+1],
        tfidf_matrix
    ).flatten()

    similar_indices = cosine_scores.argsort()[-top_n-1:-1][::-1]

    return products_df.iloc[similar_indices][
        ['asin','title','category_name','stars']
    ]

recommend("Motorcycle Helmet")

