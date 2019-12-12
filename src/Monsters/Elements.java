package Monsters;

import java.util.ArrayList;
import java.util.List;

public class Elements {

	protected String Name;//объявление переменных
	protected int Atk;
	protected int Lvl;
	protected int HP;
	protected int ElAtk,ElDef;
	protected double CountHit, CountDef;
	protected int Chance;
	
	protected static final String[] Elem = {"Fire","Tree","Water","Metal","Earth"};//создание массива названий элементов
	
	//создание дефолтных значений класса
	static String DefName="Unknown";
	static int DefAtk=100;
	static int DefLvl=1;
	static int DefHP=1000;
	static int EAtk=0, EDef=0;
	static double DefCount=0;
	static int DefChance=9; //шанс-процент срабатывания навыка (повышается на 2 каждый уровень)

	//создание массива данных элементов, для дальнейшего вывода их на экран
	protected static List<Elements> MonstersList = new ArrayList<Elements>();
	
	public Elements()//дефолтный конструктор, который ссылается на полный
	{
		this(DefName,DefAtk,DefLvl,DefHP,EAtk,EDef,DefCount,DefCount,DefChance);
	}
	
	public Elements(String Name,int ElAtk,int ElDef)//конструктор, который мы используем в программе
	{
		this(Name,DefAtk,DefLvl,DefHP,ElAtk,ElDef,DefCount,DefCount,DefChance);
	}
	
	//полный конструктор
	private Elements(String Name, int Atk, int Lvl, int HP, int ElAtk, int ElDef, double CountHit, double CountDef, int Chance)
	{
		this.Name=Name;
		this.Atk=Atk;
		this.Lvl=Lvl;
		this.HP=HP;
		this.ElAtk=ElAtk;
		this.ElDef=ElDef;
		this.CountHit=CountHit;
		this.CountDef=CountDef;
		this.Chance=Chance;
		
		MonstersList.add(this); //добавление значений в массив при вызове конструктора
	}
	
	public void ShowMonster() //Выводит данные одного объекта
 
    {
	if (this.HP>0)	//помечает знаком "=" при выводе монстра с положительным HP
    	System.out.printf("=Имя:(%10s) Уровень:(%2d) ХП: %4d; Атака: (%5s) Защита:(%5s) :: %2d-Атк/%d/, %2d-принял/%d/\n",
			this.Name,this.Lvl,this.HP,Elem[(this.ElAtk)],Elem[(this.ElDef)],
				(int)this.CountHit,(int) ((this.CountHit*100)%100),(int)this.CountDef,(int) ((this.CountDef*100)%100));
    else System.out.printf(" Имя:(%10s) Уровень:(%2d) ХП: %4d; Атака: (%5s) Защита:(%5s) :: %2d-Атк/%d/, %2d-принял/%d/\n",
			this.Name,this.Lvl,this.HP,Elem[(this.ElAtk)],Elem[(this.ElDef)],
				(int)this.CountHit,(int) ((this.CountHit*100)%100),(int)this.CountDef,(int) ((this.CountDef*100)%100));
    }
	
    public static void ShowMonstersList()
    { for (Elements M : MonstersList) M.ShowMonster(); //вызывает объект для вывода данных из массива MonsterList
    System.out.println();
    }
    
    public boolean Chancer (int CH) //метод срабатывания навыка с входным параметром для его модификации на входе
    {
    	int rnd = (int) (Math.random()*101);
    	if (rnd<CH)
    	{
    		System.out.print(rnd+"."+CH+"=СРАБОТАЛ НАВЫК!");
    		return true;
    	}
    	else return false;
    }
    
    public static void lvlUp (Elements V) //метод поднятия уровня
    {
    		V.HP+=130; V.Atk*=1.15; V.Lvl++;
    		V.Chance+=2;
    }
    
    public static int Attack (Elements N1, Elements N2) //метод атаки с учётом параметров защиты элементов
    {
    	byte A=0; //создание переменной для проверки срабатывания защиты или крита на элемент
    	int hp=N2.HP; //создание переменной с параметрами HP до удара
    	int kick=N1.Atk; //присвоение значения атаки нападающего
    	    	
    	N1.CountHit++; N2.CountDef++; //подсчёт ударов и принятых повреждений
    
    if	(N1.ElDef==3 && N1.Chancer(N1.Chance)) {kick*=1.21; System.out.print("+мт-");} //шанс увеличения атаки для элемента метал в защите моба
    if	(N2.ElDef==2 && N2.Chancer(N2.Chance/2)) {kick=1; System.out.print("+вд-");} //шанс уворота от атаки для элемента вода в защите моба
    if	(N2.ElDef==4 && N2.Chancer(N2.Chance)) {kick*=0.6; System.out.print("+зм-");} //шанс повышения защиты от атаки для элемента земля в защите моба

    	int hit=N1.ElAtk-N2.ElDef; //создание переменной разницы элементов
    	if  (hit == -1 || hit == 4) //элементы прописаны один за другим в порядке их содействия, поэтому разница -1 и 4 даёт эффект
    			{
    			N2.HP -= kick*1.2;// (увеличение параметра повреждений при подчинённом элементе)
    			N1.CountHit+=0.01; //cчётчик критических ударов, который будет вычисляться по модулю
    			A=1; //это значение идентификатора при срабатывании крит.удара по элементу
    			}
   		
    	else if (hit == 1 || hit == -4) // то же самое и в обартную сторону всего. элементов (пока) от 0 до 4 в массиве...
    	 		{
    			 N2.HP -= kick*0.8; 		// уменьшение параметра повреждений при доминантном значении защиты перед атакующим элементом
    	 		 N2.CountDef+=0.01; //счётчик защиты при срабатывании элемента
    	 		 A=-1; //это значение идентификатора при срабатывании защиты элемента
    	 		 }
    	else 	{
    			N2.HP -= kick; //при осутствии модификаторов поврежедений наносится обычный удар
    			}
   		
    if(N1.ElDef==1 && N1.Chancer(N1.Chance*2)) {N1.HP+=(hp-N2.HP)*0.35; System.out.print("+др-");} //если элемент дерево, шанс присвоить 40% отнятого при атаке ХП
    		 
    			return A; //возвращает значение 0, если не сработал ни один элемент
    }
    	
    public int getHP() {
    	return HP;
	}
    
    public int getAtk() {
    	return Atk;
	}
    
    public int getLvl() {
    	return Lvl;
	}
    public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getElAtk() {
		return Elem[ElAtk];
	}

	public void setElAtk(int elAtk) {
		ElAtk = elAtk;
	}

	public String getElDef() {
		return Elem[ElDef];
	}

	public void setElDef(int elDef) {
		ElDef = elDef;
	}
	
	public double getCountHit() {
		return CountHit;
	}

	public double getCountDef() {
		return CountDef;
	}

	public int getChance() {
		return Chance;
	}
	
	
    
}
