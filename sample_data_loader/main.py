import requests
import json
import csv


# Значения параметров
first_name = "John"
last_name = "Doe"
email = "johndoe32@example.com"
username = "johndoe32"
password = "password"

# Константы
BASE_URL = "http://localhost:8080"
REGISTER_ENDPOINT = "/api/profiles"  
LOGIN_ENDPOINT = "/auth/signin"
BONDS_ENDPOINT = "/api/admin/bonds"
CONTENT_TYPE = "application/json"

BONDS_CSV = "bonds.csv"
HISTORY_CSV = "history.csv"



# Функция для регистрации пользователя
def register_user(firstName, lastName, email, username, password):
    user_data = {
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "username": username,
        "password": password
    }
    
    full_url = BASE_URL + REGISTER_ENDPOINT
    response = requests.post(full_url, json=user_data, headers={"Content-Type": CONTENT_TYPE})
    
    if response.status_code == 201:
        print("Пользователь успешно зарегистрирован.")
    else:
        print("Ошибка при регистрации пользователя.")
        print(f"Статус код: {response.status_code}")
        print(f"Текст ответа: {response.text}")



# Функция для входа в систему и извлечения токенов
def login(username, password):
    login_data = {
        "username": username,
        "password": password
    }
    full_url = BASE_URL + LOGIN_ENDPOINT
    response = requests.post(full_url, json=login_data, headers={"Content-Type": CONTENT_TYPE})
    if response.status_code == 200:
        print("Успешный вход в систему.")
        response_data = json.loads(response.text)
        access_token = response_data["accessToken"]
        refresh_token = response_data["refreshToken"]
        return access_token, refresh_token
    else:
        print("Ошибка при входе в систему.")
        print(f"Статус код: {response.status_code}")
        print(f"Текст ответа: {response.text}")
        return None, None

# Функция для чтения данных из CSV файла и создания облигаций
def create_bond(auth_token, bond_data):
    full_url = BASE_URL + BONDS_ENDPOINT
    headers = {
        "Authorization": f"Bearer {auth_token}",
        "Content-Type": CONTENT_TYPE
    }
    response = requests.post(full_url, json=bond_data, headers=headers)
    
    if response.status_code == 200:
        bond_info = json.loads(response.text)
        bond_ticker = bond_info["ticker"]
        print(f"Облигация {bond_ticker} успешно создана.")
        return bond_info["ticker"], bond_info["id"]
    else:
        print("Ошибка при создании облигации.")
        print(f"Статус код: {response.status_code}")
        print(f"Текст ответа: {response.text}")
        return None, None

# Функция для чтения данных из CSV файла и создания облигаций
def create_bonds_from_csv(auth_token, csv_file_path):
    created_bonds = {} 
    with open(csv_file_path, mode='r', newline='', encoding='utf-8') as file:
        reader = csv.DictReader(file)
        for row in reader:
            ticker, bond_id = create_bond(auth_token, row)
            if ticker and bond_id:
                created_bonds[ticker] = bond_id
    
    return created_bonds

def add_bond_price_history(auth_token, bond_id, date, price, coupon_income):
    url = f"{BASE_URL}/api/admin/bonds/{bond_id}/price-history"
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {auth_token}"
    }

    data = {
        "date": date,
        "price": price,
        "couponIncome": coupon_income
    }
    
    response = requests.post(url, json=data, headers=headers)

    if response.status_code in [200, 201]:
        return response.json()
    else:
        return None

def create_price_history_from_csv(auth_token, csv_file_path, created_bonds):
    results = []

    with open(csv_file_path, mode='r', newline='', encoding='utf-8') as file:
        reader = csv.DictReader(file)
        for row in reader:
            ticker = row.get("ticker")
            bond_id = created_bonds.get(ticker)
            
            if bond_id:
                date = row.get("date")
                price = row.get("price")
                coupon_income = row.get("couponIncome")

                result = add_bond_price_history(auth_token, bond_id, date, price, coupon_income)
                if result:
                    print(result)
                    results.append(result)
    return results
    

register_user(first_name, last_name, email, username, password)
access_token, refresh_token = login(username, password)
created_bonds = create_bonds_from_csv(access_token, BONDS_CSV)
history_results = create_price_history_from_csv(access_token, HISTORY_CSV, created_bonds)
