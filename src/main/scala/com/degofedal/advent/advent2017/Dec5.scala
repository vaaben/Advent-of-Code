package com.degofedal.advent.advent2017

/**
--- Day 5: A Maze of Twisty Trampolines, All Alike ---

An urgent interrupt arrives from the CPU: it's trapped in a maze of jump instructions, and it would like assistance from any programs with spare cycles to help find the exit.

The message includes a list of the offsets for each jump. Jumps are relative: -1 moves to the previous instruction, and 2 skips the next one. Start at the first instruction in the list. The goal is to follow the jumps until one leads outside the list.

In addition, these instructions are a little strange; after each jump, the offset of that instruction increases by 1. So, if you come across an offset of 3, you would move three instructions forward, but change it to a 4 for the next time it is encountered.

For example, consider the following list of jump offsets:

0
3
0
1
-3
Positive jumps ("forward") move downward; negative jumps move upward. For legibility in this example, these offset values will be written all on one line, with the current instruction marked in parentheses. The following steps would be taken before an exit is found:

(0) 3  0  1  -3  - before we have taken any steps.
(1) 3  0  1  -3  - jump with offset 0 (that is, don't jump at all). Fortunately, the instruction is then incremented to 1.
 2 (3) 0  1  -3  - step forward because of the instruction we just modified. The first instruction is incremented again, now to 2.
 2  4  0  1 (-3) - jump all the way to the end; leave a 4 behind.
 2 (4) 0  1  -2  - go back to where we just were; increment -3 to -2.
 2  5  0  1  -2  - jump 4 steps forward, escaping the maze.
In this example, the exit is reached in 5 steps.

How many steps does it take to reach the exit?

Your puzzle answer was 342669.

--- Part Two ---

Now, the jumps are even stranger: after each jump, if the offset was three or more, instead decrease it by 1. Otherwise, increase it by 1 as before.

Using this rule with the above example, the process now takes 10 steps, and the offset values after finding the exit are left as 2 3 2 3 -1.

How many steps does it now take to reach the exit?

Your puzzle answer was 25136209.
  */

object Dec5 {

  val testInput = "0\n3\n0\n1\n-3"
  val input1 = "0\n1\n0\n1\n0\n-1\n0\n1\n2\n2\n-8\n-7\n-3\n1\n0\n-2\n-6\n-7\n-11\n2\n-11\n0\n-18\n0\n-18\n-1\n1\n-16\n-3\n-28\n-10\n-6\n-11\n-6\n-17\n-20\n-15\n-31\n-37\n-34\n-14\n-35\n-34\n-17\n-28\n-20\n-12\n-41\n-29\n-8\n-1\n-50\n-46\n-26\n-41\n-33\n-17\n0\n-28\n-52\n-38\n-28\n-29\n-60\n-23\n-60\n-55\n-28\n-43\n-57\n-66\n-35\n-48\n-71\n-25\n-6\n-27\n-47\n-77\n-68\n-21\n2\n-39\n-82\n-2\n-59\n-61\n-67\n-26\n-11\n0\n-68\n-85\n-10\n-62\n-49\n-28\n-15\n-34\n-55\n-92\n-92\n-37\n-82\n-49\n-86\n-25\n-24\n-81\n-86\n-6\n-48\n-79\n-22\n-30\n-1\n-63\n-77\n-64\n-70\n-86\n-118\n-36\n-44\n-50\n-70\n-76\n-5\n-72\n-72\n-84\n-1\n-104\n-116\n-18\n-69\n-78\n-23\n-99\n-69\n-32\n-26\n-4\n-134\n-22\n-18\n-70\n-95\n-13\n-136\n-73\n-131\n-24\n-101\n-136\n-29\n-132\n-154\n-108\n-127\n-48\n-134\n-122\n-162\n-2\n-61\n-9\n-4\n-126\n-146\n-161\n-157\n-116\n-95\n-83\n-36\n-86\n-57\n-42\n-103\n-73\n1\n0\n-28\n-156\n-67\n-178\n-36\n-169\n-46\n-16\n-97\n-86\n-112\n-186\n-111\n-69\n-158\n-37\n-75\n-109\n-186\n-16\n-84\n-73\n-83\n-139\n-54\n-89\n-191\n-126\n-15\n-158\n-19\n-116\n-73\n-13\n-184\n-121\n-14\n-116\n-167\n-174\n-103\n-66\n-128\n-156\n-5\n-174\n-220\n-213\n-96\n-139\n-22\n-102\n-33\n-118\n-163\n-184\n-17\n-76\n-72\n-96\n-106\n-203\n-55\n-181\n-207\n-40\n-235\n-139\n-5\n-127\n-21\n-155\n-183\n-51\n-54\n-38\n-247\n-218\n-56\n-34\n-173\n-241\n-187\n-38\n-13\n-172\n-2\n-235\n-167\n-191\n-250\n-150\n-34\n-151\n-183\n-119\n-90\n-21\n-93\n-275\n-168\n-160\n-97\n-100\n-25\n-273\n-245\n-44\n-223\n-201\n-156\n-12\n-55\n-189\n-181\n-10\n-92\n-152\n-90\n-217\n-68\n-81\n-76\n-86\n-48\n-287\n-281\n-63\n-83\n-66\n-50\n-49\n-310\n-254\n-121\n-294\n-132\n-53\n-30\n-223\n-85\n-297\n-264\n-58\n-51\n-294\n-283\n-3\n0\n-262\n-33\n-136\n-14\n-238\n-6\n-312\n-17\n-328\n-299\n-245\n-266\n-6\n-330\n-117\n-172\n-260\n-224\n-139\n-156\n-165\n-13\n-243\n-173\n-42\n-67\n-7\n-148\n-1\n-105\n-205\n-223\n-122\n-82\n-221\n-317\n-330\n-240\n-189\n-12\n-268\n-243\n-177\n-120\n-320\n-127\n-351\n-178\n-219\n-351\n-128\n-28\n-227\n-188\n-195\n-205\n-204\n-283\n-316\n-276\n-319\n-312\n-337\n-318\n-136\n-33\n-307\n-397\n-387\n-303\n-12\n-347\n-112\n-171\n-222\n-358\n-215\n-71\n-99\n-108\n-24\n-291\n-344\n-97\n-99\n-6\n-270\n-327\n-32\n-387\n-402\n-13\n-175\n-243\n-374\n-422\n-382\n-152\n-420\n-266\n-326\n-37\n-215\n-357\n-423\n-16\n-272\n-357\n-87\n-184\n-21\n-351\n-300\n-219\n-390\n-12\n-15\n-78\n-69\n-35\n-308\n-303\n-300\n-265\n-440\n-19\n-117\n-87\n-218\n-163\n-317\n-42\n-55\n-185\n-245\n-196\n-183\n-327\n-467\n-102\n-432\n-162\n-202\n-39\n-179\n-301\n-237\n-299\n-33\n-198\n-127\n-138\n-454\n-46\n-87\n-362\n-448\n-382\n-42\n-358\n-475\n-350\n-50\n-380\n-316\n-380\n-463\n-108\n-405\n-139\n-480\n-30\n-212\n-308\n-239\n-223\n-306\n-81\n-89\n-172\n-304\n-87\n-380\n-394\n-507\n-392\n-98\n-403\n-155\n-13\n-197\n-66\n-244\n-401\n-278\n-391\n-64\n-460\n-368\n-178\n-145\n-440\n-49\n-369\n-418\n-332\n-200\n-294\n-495\n-104\n-5\n-261\n-168\n-392\n-230\n-154\n-472\n-404\n-472\n-307\n-256\n-169\n-330\n-500\n-365\n-146\n-133\n-84\n-336\n-405\n-555\n-74\n-68\n-354\n-552\n-108\n-80\n-406\n-164\n-119\n-487\n-151\n-113\n-244\n-471\n-80\n-312\n-495\n-556\n-76\n-24\n-546\n-493\n-340\n-464\n-328\n-7\n-474\n-246\n-237\n-40\n-199\n-346\n-330\n-139\n-284\n-435\n-83\n-210\n-423\n-361\n-56\n-271\n-140\n-162\n-232\n-391\n-42\n-99\n-590\n2\n-271\n-101\n-114\n-117\n-310\n-502\n-287\n-319\n-323\n-362\n-551\n-439\n-533\n-183\n-404\n-401\n-343\n-36\n-89\n-454\n-128\n-611\n-6\n-619\n-110\n-389\n-290\n-270\n-375\n-283\n-472\n-65\n-195\n-129\n-61\n-548\n-151\n-74\n-612\n-156\n-371\n-42\n-447\n-565\n-394\n-550\n-476\n-592\n-262\n-96\n-529\n-395\n-204\n-491\n-167\n-186\n-527\n-508\n-245\n-455\n-552\n-672\n-338\n-269\n-104\n-240\n-77\n-303\n-227\n-453\n-126\n-294\n-572\n-8\n-527\n-361\n-438\n-457\n-513\n-560\n-442\n-649\n-321\n-123\n-52\n-166\n-320\n-301\n-570\n-684\n-325\n-515\n-547\n-52\n-221\n-488\n-182\n-618\n-109\n-497\n-167\n-288\n-358\n-334\n-313\n-288\n-102\n-409\n-143\n-204\n-216\n-681\n-512\n-245\n-301\n-35\n-262\n-239\n-405\n-682\n-715\n-438\n-314\n-179\n-611\n-667\n-622\n-511\n-463\n-370\n-338\n-434\n-580\n-637\n-201\n-213\n-357\n-443\n-382\n-315\n-483\n-399\n-624\n-318\n-226\n-652\n-638\n-743\n-330\n-647\n-146\n-138\n-698\n-511\n-173\n-663\n-333\n-564\n-160\n-239\n-243\n-91\n-65\n-468\n-256\n-197\n-210\n-575\n-420\n-715\n-681\n-454\n-226\n-226\n-339\n-473\n-737\n-62\n-149\n-351\n-770\n-313\n-216\n-491\n-511\n-269\n-628\n-391\n-429\n-110\n-199\n-409\n-516\n-7\n-433\n-405\n-792\n-685\n-615\n-287\n-385\n-627\n-527\n-426\n-626\n-164\n-767\n-794\n-115\n-483\n-323\n-371\n-679\n-772\n-808\n-2\n-16\n-459\n-749\n-569\n-139\n-7\n-555\n-161\n-613\n-230\n-771\n-825\n-241\n-579\n-710\n-73\n-790\n-653\n-655\n-394\n-218\n-711\n-467\n-774\n-694\n-664\n-357\n-29\n-121\n-643\n-742\n-388\n-633\n-440\n-755\n-581\n-661\n-653\n-536\n-596\n-10\n-796\n-230\n-813\n-125\n-540\n-584\n-389\n-144\n-346\n-213\n-444\n-205\n-712\n-651\n-670\n-139\n-60\n-620\n-49\n-284\n-212\n-452\n-520\n-243\n-356\n-348\n-442\n-585\n-202\n-207\n-222\n-47\n-49\n-408\n-571\n-154\n-695\n-802\n-524\n-523\n-617\n-615\n-571\n-92\n-344\n-675\n-613\n-759\n-29\n-833\n-662\n-223\n-46\n-156\n-373\n-412\n-848\n-93\n-695\n-250\n-810\n-477\n-150\n-282\n-789\n-193\n-443\n-193\n-159\n-840\n-755\n-508\n-404\n-307\n-80\n-320\n-14\n-245\n-746\n-610\n-855\n-552\n-323\n-366\n-45\n-16\n-335\n-852\n-46\n-459\n-461\n-537\n-547\n-180\n-842\n-213\n-447\n-712\n-633\n-362\n-953\n-407\n-47\n0\n-466\n-107\n-648\n-528\n-413\n-828\n-217\n-484\n-969\n-121\n-858\n-208\n-618\n-384\n-16\n-91\n-662\n-348\n-675\n-63\n-713\n-966\n-678\n-293\n-827\n-445\n-387\n-212\n-763\n-847\n-756\n-299\n-443\n-80\n-286\n-954\n-521\n-394\n-357\n-861\n-530\n-649\n-671\n-437\n-884\n-606\n-73\n-452\n-354\n-729\n-927\n-248\n-2\n-738\n-521\n-440\n-435\n-291\n-104\n-402\n-375\n-875\n-686\n-812\n-539\n-934\n-536\n-924\n-924\n-365"

  def parseInput(str: String): List[Int] = {
    str.split("\n").toList.map(_.toInt)
  }

  def step(pos: Int, jumpList: List[Int]): (Int, List[Int]) = {
    val jump = jumpList(pos)
    val (front,end) = jumpList.splitAt(pos)

    (pos + jump, front ::: (jump+1 :: end.tail) )
  }

  def step_alt(pos: Int, jumpList: List[Int]): (Int, List[Int]) = {
    val jump = jumpList(pos)
    val (front,end) = jumpList.splitAt(pos)

    val correction = if(jump >= 3) {-1} else {1}

    (pos + jump, front ::: (jump+correction :: end.tail) )
  }

  def run(jumpList: List[Int]): Int = {
    var steps = 0

    var l = jumpList
    var pos = 0
    while(pos >= 0 && pos < jumpList.size) {
      val x = step(pos, l)
      steps += 1
      pos = x._1
      l = x._2
      //println(x)
    }

    steps
  }

  def run_alt(jumpList: List[Int]): Int = {
    var steps = 0

    var l = jumpList
    var pos = 0
    while(pos >= 0 && pos < jumpList.size) {
      val x = step_alt(pos, l)
      steps += 1
      pos = x._1
      l = x._2
      //println(x)
    }

    steps
  }

  def main(args: Array[String]): Unit = {
    var jumpList = parseInput(testInput)

    println(run(jumpList))

    //jumpList = parseInput(input1)

    //println(run(jumpList))

    println(run_alt(jumpList))

    jumpList = parseInput(input1)

    println(run_alt(jumpList))
  }

}
