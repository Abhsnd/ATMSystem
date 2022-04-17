package com.winghao;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        // 存储账户对象
        ArrayList<Account> accounts = new ArrayList<>();

        // 系统的首页：登录和开户
        showMain(accounts);
    }

    // 系统首页
    private static void showMain(ArrayList<Account> accounts) {
        System.out.println("=============欢迎进入首页=================");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请您输入您的操作: ");
            System.out.println("1. 登录");
            System.out.println("2. 开户");
            System.out.println("请您输入指令: ");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    login(accounts, sc);    // 登录
                    break;
                case 2:
                    register(accounts, sc); // 开户
                    break;
                default:
                    System.out.println("您当前输入的指令有误！");
            }
        }
    }


    /**
     * 用户开户功能
     * @parm accounts: 账户的集合对象
     */
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("===============用户开户功能==============");
        // 键盘输入 姓名、密码、确认密码
        System.out.println("请您输入开户名称：");
        String name = sc.next();

        String password = "";
        while (true) {
            System.out.println("请您输入开户密码：");
            password = sc.next();
            System.out.println("请您输入确认密码：");
            String checkPassword = sc.next();
            // 检查两次密码是否一致
            if (checkPassword.equals(password)) {
                break;
            } else {
                System.out.println("两次密码必须一致~~~");
            }
        }

        System.out.println("请您输入单次限额：");
        double quotaMoney = sc.nextDouble();

        // 创建卡号，卡号为8位，不能与其他账户卡号重复
        String cardId = createCardId(accounts);

        // 创建一个用户账户对象封装账户信息
        Account account = new Account(cardId, name, password, quotaMoney);

        // 把账户对象添加到集合中
        accounts.add(account);
        System.out.println("恭喜您，您开户成功，您的卡号是：" + account.getCardId() + "。请您妥善保管");
    }

    // 创建卡号
    public static String createCardId(ArrayList<Account> accounts) {
        while (true) {
            // 生成8位随机数字作为卡号
            String cardId = "";
            Random random = new Random();
            for (int i = 0; i < 8; i++) {
                cardId += random.nextInt();
            }
            // 判断卡号是否重复
            Account account = getAccountsByCardId(cardId, accounts);
            if (account == null) {      // 说明当前卡号没有重复
                return cardId;
            }
        }
    }

    // 通过卡号获取账户
    private static Account getAccountsByCardId(String cardId, ArrayList<Account> accounts) {
        // 根据卡号查询账户对象
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getCardId().equals(cardId)) {
                return account;
            }
        }
        return null;    // 差不多账户，说明卡号没有重复
    }


    /**
     * 用户登录
     * @parm accounts
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        // 系统中必须存在账户才可以登录
        if (accounts.size() == 0) {     // 没有账户
            System.out.println("当前系统中无任何账户，您需要先注册！");
            return;
        }

        // 用户键盘输入卡号
        while (true) {
            System.out.println("请您输入登录的卡号：");
            String cardId = sc.next();
            // 根据卡号查询账户对象
            Account account = getAccountsByCardId(cardId, accounts);
            // 判断账户对象是否存在，存在说明卡号没问题
            if (account != null) {
                while (true) {
                    // 输入密码
                    System.out.println("请您输入登录的密码：");
                    String password = sc.next();
                    // 判断密码是否正确
                    if (account.getPassword().equals(password)) {
                        // 密码正确，登录成功
                        System.out.println("恭喜您，" + account.getUsername() + "先生/女士成功进入系统，您的卡号是：" + account.getCardId());
                        // 展示系统登录后的操作界面
                        showUserCommand(sc, account, accounts);
                        return;
                    } else {
                        System.out.println("您的密码有误，请确认！");
                    }
                }
            } else {
                System.out.println("对不起，不存在该卡号的账户！");
            }
        }
    }


    /**
     * 展示登录后的操作界面
     */
    private static void showUserCommand(Scanner sc, Account account, ArrayList<Account> accounts) {
        while (true) {
            System.out.println("==================用户操作界面===================");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、修改密码");
            System.out.println("6、退出");
            System.out.println("7、注销账户");
            System.out.println("请您输入操作命令：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    // 查询账户
                    showAccount(account);
                    break;
                case 2:
                    // 存款
                    depositMoney(account, sc);
                    break;
                case 3:
                    // 取款
                    drawMoney(account,sc);
                    break;
                case 4:
                    // 转账
                    transferMoney(accounts, account , sc);
                    break;
                case 5:
                    // 修改密码
                    updatePassWord(account,sc);
                    return; // 结束当前操作的方法
                case 6:
                    // 退出
                    System.out.println("欢迎下次光临！！");
                    return; // 结束当前操作的方法！
                case 7:
                    // 注销账户
                    accounts.remove(account);       // 从当前集合中移除当前账户
                    System.out.println("销户成功了！！");
                    return;
                default:
                    System.out.println("您的命令输入有误~~~");
            }
        }
    }

    /**
     * 查找账户
     * @param account
     */
    private static void showAccount(Account account) {
        System.out.println("==================当前账户详情===================");
        System.out.println("卡号: " + account.getCardId());
        System.out.println("姓名: " + account.getUsername());
        System.out.println("余额: " + account.getMoney());
        System.out.println("单次限额: " + account.getQuotaMoney());
    }

    /**
     * 存钱
     * @param account
     * @param sc
     */
    private static void depositMoney(Account account, Scanner sc) {
        System.out.println("==================存钱操作===================");
        System.out.println("请您输入存款的金额：");
        double money = sc.nextDouble();
        // 更新存款金额
        account.setMoney(account.getMoney() + money);
        System.out.println("存款完成！！");
        showAccount(account);
    }

    /**
     * 取款
     * @param account
     * @param sc
     */
    private static void drawMoney(Account account, Scanner sc) {
        System.out.println("==================取款操作===================");
        // 判断账户是否足够100元
        if (account.getMoney() >= 100) {
            while (true) {
                System.out.println("请您输入取款的金额：");
                double money = sc.nextDouble();
                // 判断取款金额是否超过单次限额
                if (money > account.getQuotaMoney()) {
                    System.out.println("您当次取款金额超过每次限额，不要取那么多，每次最多可以取：" + account.getQuotaMoney());
                } else {
                    // 判断当前余额是否足够取款
                    if (account.getMoney() >= money) {
                        account.setMoney(account.getMoney() - money);
                        System.out.println("恭喜您，取钱" + money + "成功了！当前账户还剩余：" + account.getMoney());
                        return;
                    } else {
                        System.out.println("余额不足啊！");
                    }
                }
            }
        } else {
            System.out.println("您自己的金额没有超过100元，就别取了~~~");
        }
    }

    /**
     * 转账
     * @param accounts
     * @param account
     * @param sc
     */
    private static void transferMoney(ArrayList<Account> accounts, Account account, Scanner sc) {
        // 判断系统中是否存在两位以上的账户
        if (accounts.size() < 2) {
            System.out.println("对不起，系统中无其他账户，您不可以转账！");
            return;
        }
        // 判断自己的账户是否有钱
        if (account.getMoney() == 0) {
            System.out.println("对不起，您自己都没钱，就别转了~~");
            return;
        }

        // 开始转账
        while (true) {
            System.out.println("请您输入对方账户的卡号：");
            String cardId = sc.next();
            Account otherAccount = getAccountsByCardId(cardId, accounts);
            // 判断这个账户对象是否存在，存在则说明对方卡号正确
            if (otherAccount != null) {
                // 判断这个账户对象是否是当前登录的账户自己
                if (otherAccount.getCardId().equals(account.getCardId())) {     // 给自己转账
                    System.out.println("您不可以为自己转账！");
                } else {
                    // 确认对方姓氏
                    String name = "*" + otherAccount.getUsername().substring(1);
                    System.out.println("请您确认【" + name + "】的姓氏：");
                    String preName = sc.next();
                    if (otherAccount.getUsername().startsWith(preName)) {
                        // 验证通过，开始实际转账
                        System.out.println("请您输入转账的金额：");
                        double money = sc.nextDouble();
                        // 判断是否超过自己的余额
                        if (money > account.getMoney()) {   // 超过余额
                            System.out.println("对不起，您要转账的金额太多，您最多可以转账多少：" + account.getMoney());
                        } else {
                            // 执行转账操作
                            account.setMoney(account.getMoney() - money);               // 转出账户钱减少
                            otherAccount.setMoney(otherAccount.getMoney() + money);     // 转入账户钱增加
                            System.out.println("恭喜您，转账成功了，已经为" + otherAccount.getUsername() + "转账多少: " + money);
                            showAccount(account);   // 展示当前账户信息
                            return;
                        }
                    } else {
                        System.out.println("对不起，您认证的信息有误~~~");
                    }
                }
            } else {
                System.out.println("对不起，您输入的转账卡号有问题！");
            }
        }
    }

    /**
     * 修改密码
     * @param account
     * @param sc
     */
    private static void updatePassWord(Account account, Scanner sc) {
        System.out.println("===========修改密码=======================");
        while (true) {
            System.out.println("请您输入正确的密码：");
            String password = sc.next();
            // 判断密码是否正确
            if (account.getPassword().equals(password)) {
                System.out.println("请您输入新的密码：");
                String newPassword = sc.next();
                System.out.println("请您输入确认密码：");
                String checkNewPassword = sc.next();
                if (checkNewPassword.equals(newPassword)) {
                    account.setPassword(newPassword);   // 更新当前账户密码
                    return;
                } else {
                    System.out.println("您两次输入的密码不一致~~");
                }
            } else {
                System.out.println("当前输入的密码不正确~~~");
            }
        }
    }

}
