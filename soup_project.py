#------------------LIBRARIES------------------#
import pandas as pd #library for tables
import requests #library for scraping HTML
from bs4 import BeautifulSoup #library for scraping table from HTML
#make sure to have the google API library installed

#replace url with any url needing to be decoded in the same way
website = 'https://docs.google.com/document/d/e/2PACX-1vTER-wL5E8YC9pxDx43gk8eIds59GtUUk4nJo_ZWagbnrH0NFvMXIw6VWFLpf5tWTZIT9P9oLIoFJ6A/pub'

def scrapeTableToGrid(url):
    #scrape HTML
    requests.get(url)
    html_file = requests.get(url)
    
    #extract table from HTML
    soup = BeautifulSoup(html_file.text, 'html.parser')
    table = soup.find('table')
    
    #create dataframe from scraped table
    data = []
    for row in table.find_all('tr'):
        columns = row.find_all('td')
        row = [row.text for row in columns]
        data.append(row)
    df = pd.DataFrame(data, columns=['x-coordinate', 'Character', 'y-coordinate'])
    
    #remove table header
    df_excluded = df.iloc[1:]
    
    #convert coordinates to Int for sorting
    df_excluded['x-coordinate'] = df_excluded['x-coordinate'].str.strip().astype('int')
    df_excluded['y-coordinate'] = df_excluded['y-coordinate'].str.strip().astype('int')
    
    #create sorted dataframe
    sorted_df = df_excluded.sort_values(by=['x-coordinate', 'y-coordinate'], ascending=[True,True])
    
    #form sorted dataframe into grid table
    df_pivot = sorted_df.pivot(index = 'y-coordinate', columns = 'x-coordinate')
    
    #fill nan values with blank
    df_fill = df_pivot.fillna('')
    
    #unrestrict panda display settings
    pd.set_option('display.max_rows', 100)
    pd.set_option('display.max_columns', 100)
    pd.set_option('display.width',1000)

    print(df_fill)
    #export to csv (optional)
    #df_fill.to_csv('secret_message.csv', encoding='utf-8', index=False)

#function call
scrapeTableToGrid(website)    

